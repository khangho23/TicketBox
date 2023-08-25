package com.example.demo.listener;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class MyEmail extends ApplicationEvent {
    public String email;

    // Mọi Class kế thừa ApplicationEvent sẽ phải gọi Constructor tới lớp cha.
    public MyEmail(Object source, String email) {
        // Object source là object tham chiếu tới nơi đã phát ra event này!
        super(source);
        this.email = email;
    }
}
