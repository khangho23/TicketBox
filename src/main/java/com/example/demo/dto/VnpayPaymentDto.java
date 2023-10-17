package com.example.demo.dto;

import com.example.demo.model.VnpayModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnpayPaymentDto extends VnpayModel {
    private String vnp_CurrCode;
    private String vnp_OrderType;
    private String vnp_ReturnUrl;
    private String vnp_IpAddr;
    private String vnp_Locale= "vn";
    private String vnp_Command;
    private String vnp_ExpireDate;
    private String vnp_CreateDate;
    private String vnp_Version;
    private String redirect_url;
}
