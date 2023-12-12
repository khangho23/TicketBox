package com.example.demo.service;

import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.dao.BillDao;
import com.example.demo.dto.BillDetailsDto;
import com.example.demo.dto.BillTicketDto;
import com.example.demo.dto.BillToppingDetailsDto;
import com.example.demo.dto.BillHistoryDto;
import com.example.demo.dto.TicketDto;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.ToppingDetails;
import com.example.demo.entity.ToppingOfBranch;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.RateAndReviewBillModel;
import com.example.demo.model.ReviewModel;

import org.joda.time.IllegalFieldValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BillService {
	@Autowired
	BillDao billDao;

	@Autowired
	TicketService ticketService;

	@Autowired
	ToppingService toppingService;

	public Bill findById(Optional<Integer> id) {
		id.orElseThrow();
		return billDao.findById(id.get());
	}

	public List<BillHistoryDto> getBillHistory(Optional<Integer> customerId) throws InvalidRequestParameterException {
		if (customerId.isEmpty())
			throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);

		return billDao.getBillHistory(customerId.get());
	}

	public BillDetailsDto getBillDetails(Optional<Integer> billId, Optional<Integer> customerId)
			throws InvalidRequestParameterException {
		if (billId.isEmpty() || customerId.isEmpty())
			throw new InvalidRequestParameterException("Bill Details", RequestParameterEnum.NOTHING);
		List<TicketDto> tickets = ticketService.findByBillId(billId);
		BillDetailsDto billDetails = billDao.getBillDetails(billId.get(), customerId.get());
		billDetails.setTickets(tickets);

		return billDetails;
	}

	public Integer insertBillAndTicket(Optional<BillTicketDto> billTicketDto) throws InvalidRequestParameterException {
		AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);

		if (billTicketDto.isEmpty())
			throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);

		if (billTicketDto.get().getCustomerId() == null)
			throw new InvalidRequestParameterException("Customer ID", RequestParameterEnum.NOTHING);

		billTicketDto.get().setExportStatus(PaymentStatus.PENDING.getValue());
		billDao.insert(billTicketDto.get());
		billTicketDto.get().getTickets().stream().forEach(ticket -> {
			Optional<Ticket> optionalTicket = Optional.of(ticket);
			double vat = optionalTicket.get().getTotalPrice() * optionalTicket.get().getVat();

			try {
				optionalTicket.get().setBillId(billTicketDto.get().getId());
				totalPrice.updateAndGet(price -> price + optionalTicket.get().getTotalPrice() + vat);
				ticketService.insert(optionalTicket);
			} catch (InvalidRequestParameterException e) {
				e.printStackTrace();
			}
		});

		if (totalPrice.get() != 0)
			billDao.updateTotalPrice(billTicketDto.get().getId(), totalPrice.get());

		return billTicketDto.get().getId();
	}

	public Integer insertToppingDetailsInBill(Optional<BillToppingDetailsDto> billToppingDetails)
			throws InvalidRequestParameterException {
		Integer billId = billToppingDetails.get().getBillId();

		if (billId == null)
			throw new InvalidRequestParameterException("Bill ID", RequestParameterEnum.NOTHING);

		Double defaultPrice = billDao.findById(billId).getTotalPrice();
		AtomicReference<Double> totalPrice = new AtomicReference<>(defaultPrice);

		billToppingDetails.get().getToppingDetails().stream().forEach(topping -> {
			Optional<ToppingDetails> optionalTopping = Optional.of(topping);

			try {
				ToppingOfBranch toppingOfBranch = toppingService
						.findToppingOfBranchById(optionalTopping.get().getToppinngOfBranchId());
				optionalTopping.get().setBillId(billId);
				totalPrice.updateAndGet(
						price -> price + optionalTopping.get().getPriceWhenBuy() * optionalTopping.get().getQuantity());
				if (optionalTopping.get().getQuantity() > toppingOfBranch.getQuantity())
					throw new IllegalFieldValueException("quantity", "" + optionalTopping.get().getQuantity());

				toppingService.orderTopping(optionalTopping);
				toppingService.updateToppingOfBranchAfterOrdered(optionalTopping.get().getToppinngOfBranchId(),
						optionalTopping.get().getQuantity());
			} catch (InvalidRequestParameterException e) {
				e.printStackTrace();
			}
		});

		if (totalPrice.get() != defaultPrice)
			billDao.updateTotalPrice(billId, totalPrice.get());

		return billId;
	}

	public BillDetailsDto findBillDetailsByQrCode(Optional<String> qrCode) throws InvalidRequestParameterException {
		// qrCode.orElseThrow();
		// if (qrCode.get().length() != 32) throw new
		// InvalidRequestParameterException("QR code", RequestParameterEnum.WRONG);

		BillDetailsDto billDetailsDto = billDao.findBillDetailsByQrCode(qrCode.get());
		if (billDetailsDto == null)
			throw new InvalidRequestParameterException("QR code", RequestParameterEnum.NOT_EXISTS);

		return billDetailsDto;
	}

	private String generateUniqueUUID() {
		UUID uuid = null;
		boolean isUnique = false;

		while (!isUnique) {
			uuid = UUID.randomUUID();
			if (billDao.findBillDetailsByQrCode(uuid.toString()) == null) {
				isUnique = true;
			}
		}

		return uuid.toString();
	}

	public int updateRateAndReview(RateAndReviewBillModel model) {
		return billDao.updateRateAndReview(model);
	}

	public List<Bill> findByMovie(String id) {
		return billDao.findByMovie(id);
	}

	public int updateExportStatus(Optional<Integer> id, Optional<Integer> exportstatus)
			throws InvalidRequestParameterException {
		id.orElseThrow(() -> new InvalidRequestParameterException("Bill id", RequestParameterEnum.NOTHING));
		exportstatus.orElseThrow(
				() -> new InvalidRequestParameterException("Bill exportstatus", RequestParameterEnum.NOTHING));
		return billDao.updateExportStatus(id.get(), exportstatus.get());
	}

	public BillDetailsDto checkout(Optional<Integer> billId, Optional<Integer> customerId)
			throws InvalidRequestParameterException {
		customerId.orElseThrow(
				() -> new InvalidRequestParameterException("Customer Id", RequestParameterEnum.NOT_EXISTS));

		billId.orElseThrow(() -> new InvalidRequestParameterException("Checkout", RequestParameterEnum.NOT_EXISTS));
		BillDetailsDto billCheckout = billDao.checkout(billId.get(), customerId.get());

		if (billCheckout == null)
			throw new InvalidRequestParameterException("Checkout", RequestParameterEnum.NOT_FOUND);

		return billCheckout;
	}

	public ReviewModel getReviewByMovieId(String id, Integer pageSize, Integer page) {
		return new ReviewModel(billDao.getReviewByMovieId(id, pageSize, page), billDao.getTotalReviewByMovieId(id));
	}

	public void updateQrCode(Optional<Integer> id, String qrCode) throws InvalidRequestParameterException {
		id.orElseThrow(
				() -> new InvalidRequestParameterException("Bill id", RequestParameterEnum.NOT_EXISTS));

		billDao.updateQrCode(id.get(), qrCode);
	}
}
