package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOrderModel {
    List<Ticket> listTicket;
    Bill bill;
    String qrcode;
}
