package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VnpayToken {
    private String vnp_amount;
    private String vnp_app_user_id;
    private String vnp_bank_code = "";
    private String vnp_bank_tran_no;
    private String vnp_cancel_url;
    private String vnp_card_type;
    private String vnp_command;
    private String vnp_create_date;
    private String vnp_curr_code;
    private String vnp_ip_addr;
    private String vnp_locale= "vn";
    private String vnp_message;
    private String vnp_pay_date;
    private String vnp_response_code;
    private String vnp_return_url;
    private String vnp_secure_hash;
    private String vnp_store_token;
    private String vnp_token;
    private String vnp_tmn_code;
    private String vnp_transaction_no;
    private String vnp_transaction_status;
    private String vnp_txn_desc;
    private String vnp_txn_ref;
    private String vnp_version;
    private String redirect_url;
}
