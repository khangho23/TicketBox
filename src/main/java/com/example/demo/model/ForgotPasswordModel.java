package com.example.demo.model;

import lombok.Data;

@Data
public class ForgotPasswordModel {
    private String email;
    private String userToken;
}