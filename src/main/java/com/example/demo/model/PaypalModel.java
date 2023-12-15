package com.example.demo.model;

import com.example.demo.entity.PaymentDetails;

import lombok.Data;

@Data
public class PaypalModel extends PaymentDetails{
    Integer customerId;
}
