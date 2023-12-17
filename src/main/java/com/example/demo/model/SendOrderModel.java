package com.example.demo.model;

import java.util.List;

import com.example.demo.entity.Bill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOrderModel {
    InfoUserOrderModel info;
    List<ListTicketModel> listTicket;
    Bill bill;
    String paymentMethod;
}
