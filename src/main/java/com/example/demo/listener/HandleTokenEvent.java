package com.example.demo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.demo.admin.controller.enums.RequestParameterEnum;
import com.example.demo.constant.Constants;
import com.example.demo.entity.Customer;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.CustomerService;

@Component
public class HandleTokenEvent {
    @Autowired
    CustomerService customerService;

    @Async
    @EventListener
    public void resetToken(MyEmail email)
            throws InterruptedException, InvalidRequestParameterException {
        Customer customer = customerService.findByEmail(email.getEmail()).orElseThrow(() -> new InvalidRequestParameterException("Email", RequestParameterEnum.NOT_EXISTS));
        Thread.sleep(Constants.TIMETOKEN_ACTIVE);
        customer.setToken(null);
        customerService.updateToken(customer);
    }
}
