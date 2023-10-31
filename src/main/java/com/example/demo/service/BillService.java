package com.example.demo.service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.dao.BillDao;
import com.example.demo.dto.BillDetailsDto;
import com.example.demo.dto.BillHistoryDto;
import com.example.demo.dto.TicketDto;
import com.example.demo.entity.Bill;
import com.example.demo.entity.Ticket;
import com.example.demo.enums.PaymentStatus;
import com.example.demo.exception.InvalidRequestParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillDao billDao;
    
    @Autowired
    TicketService ticketService;

    public List<BillHistoryDto> getBillHistory(Optional<Integer> customerId) throws InvalidRequestParameterException {
        if (customerId.isEmpty())
            throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);

        return billDao.getBillHistory(customerId.get());
    }

    public BillDetailsDto getBillDetails(Optional<Integer> billId, Optional<Integer> customerId) throws InvalidRequestParameterException {
        if (billId.isEmpty() || customerId.isEmpty())
            throw new InvalidRequestParameterException("Bill Details", RequestParameterEnum.NOTHING);
        List<TicketDto> tickets = ticketService.findByBillId(billId);
        BillDetailsDto billDetails = billDao.getBillDetails(billId.get(), customerId.get());
        billDetails.setTickets(tickets);

        return billDetails;
    }

//    public String insertBill(Optional<Bill> bill) throws InvalidRequestParameterException {
//    	if (bill.isEmpty()) throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);
//    	
//    	billDao.insert(bill.get());
//        return RequestStatusEnum.SUCCESS.getResponse();
//    }
    
    public String insertBill(Optional<Bill> bill, Optional<List<Ticket>> tickets) throws InvalidRequestParameterException {
    	if (tickets.isEmpty()) throw new InvalidRequestParameterException("Ticket", RequestParameterEnum.NOT_FOUND);
    	else {
    		if (bill.isEmpty()) throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);
    		bill.get().setExportStatus(PaymentStatus.PENDING.getValue());
    		billDao.insert(bill.get());
    		
    		tickets.get().stream().forEach(ticket -> {
    			Optional<Ticket> optionalTicket = Optional.of(ticket);
    			
    			try {
    				optionalTicket.get().setBillId(bill.get().getId());
					ticketService.insert(optionalTicket);
				} catch (InvalidRequestParameterException e) {
					e.printStackTrace();
				}
    		});
    	}
    	
        return RequestStatusEnum.SUCCESS.getResponse();
    }
}
