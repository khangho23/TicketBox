package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VnpayModel {
    private String vnp_BankCode = "";
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private String vnp_OrderInfo;
    private String vnp_Amount;
    private String vnp_SecureHash;
}
