package com.example.demo.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ListenerEvent {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void checkTokenEvent(String guestName) {
        // Phát ra một sự kiện HandleTokenEvent
        // source (Nguồn phát ra) chính là class này
        applicationEventPublisher.publishEvent(new MyEmail(this, guestName));
    }
}
