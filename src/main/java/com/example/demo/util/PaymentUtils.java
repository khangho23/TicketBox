package com.example.demo.util;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.dto.VnpayPaymentDto;
import com.example.demo.dto.VnpayToken;
import com.example.demo.exception.InvalidRequestParameterException;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Component
public class PaymentUtils {
    public String validateBankTransferContent(String content) throws InvalidRequestParameterException {
        boolean isValid = content.matches("^[A-Za-z0-9_\\s]*$");

        if (!isValid) {
            throw new InvalidRequestParameterException("Bank Transfer Content", RequestParameterEnum.WRONG);
        }

        return content;
    }
}
