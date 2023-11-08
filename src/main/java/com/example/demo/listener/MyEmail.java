package com.example.demo.listener;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class MyEmail extends ApplicationEvent {
    public String email;

    public MyEmail(Object source, String email) {
        super(source);
        this.email = email;
    }
}
