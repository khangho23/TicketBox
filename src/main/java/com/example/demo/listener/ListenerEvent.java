package com.example.demo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ListenerEvent {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void checkTokenEvent(String guestName) {
        applicationEventPublisher.publishEvent(new MyEmail(this, guestName));
    }
}
