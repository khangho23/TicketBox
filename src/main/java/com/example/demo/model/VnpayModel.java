package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VnpayModel {
    private String vnp_Version;
    private String vnp_Command;
    private String vnp_TmnCode;
    private Integer vnp_Amount;
    private String vnp_CurrCode;
    private String vnp_BankCode;
    private String vnp_TxnRef;
    private String vnp_OrderInfo;
    private String vnp_OrderType;
    private String vnp_Locale;
    private String vnp_ReturnUrl;
    private String vnp_IpAddr;
    private String vnp_CreateDate;
    private String vnp_ExpireDate;
    private String redirect_url;
}
