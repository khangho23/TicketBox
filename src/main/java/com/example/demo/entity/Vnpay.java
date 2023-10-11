package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;

//@Entity
//@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vnpay {
    private String vnp_BankCode = "";
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private String vnp_OrderInfo;
    private String vnp_Amount;
    private String vnp_SecureHash;
}
