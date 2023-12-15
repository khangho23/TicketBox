package com.example.demo.service;

import com.example.demo.dao.PaymentDetailsDao;
import com.example.demo.entity.PaymentDetails;
import com.example.demo.enums.RequestParameterEnum;
import com.example.demo.exception.InvalidRequestParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentDetailsDao paymentDetailsDao;

    public void insertPaymentDetails(PaymentDetails paymentDetails) throws InvalidRequestParameterException {
        paymentDetailsDao.insert(paymentDetails);
    }

    public void updateStatusByBillId(Optional<Integer> billId, Integer status) throws InvalidRequestParameterException {
        billId.orElseThrow(() -> new InvalidRequestParameterException("Payment billId", RequestParameterEnum.NOT_EXISTS));
        
    	paymentDetailsDao.updateStatusByBillId(billId.get(), status);
    }

    public PaymentDetails findByTransactionNo(Optional<String> vnp_TransactionNo) throws InvalidRequestParameterException {
        vnp_TransactionNo.orElseThrow();

        return paymentDetailsDao.findByTransactionNo(vnp_TransactionNo.get());
    }

    public Optional<PaymentDetails> findByBillId(Integer billId){
        return paymentDetailsDao.findByBillId(billId);
    }
}
