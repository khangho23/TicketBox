package com.example.demo.service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.admin.controller.enums.RequestStatusEnum;
import com.example.demo.dao.BillDao;
import com.example.demo.dto.BillDetailsDto;
import com.example.demo.dto.BillHistoryDto;
import com.example.demo.dto.TicketDto;
import com.example.demo.entity.Bill;
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

    public String insertBill(Optional<Bill> bill) throws InvalidRequestParameterException {
    	if (bill.isEmpty()) throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);
    	
    	billDao.insert(bill.get());
        return RequestStatusEnum.SUCCESS.getResponse();
    }
}
