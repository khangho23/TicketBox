package com.example.demo.service;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.dao.BillDao;
import com.example.demo.dto.BillHistory;
import com.example.demo.exception.InvalidRequestParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillDao billDao;

    public List<BillHistory> getBillHistory(Optional<Integer> customerId) throws InvalidRequestParameterException {
        if (customerId.isEmpty())
            throw new InvalidRequestParameterException("Bill", RequestParameterEnum.NOTHING);

        return billDao.getBillHistory(customerId.get());
    }
}
