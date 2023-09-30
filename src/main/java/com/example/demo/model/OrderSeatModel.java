package com.example.demo.model;

import com.example.demo.dto.SeatHasCheckTicket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSeatModel {
    private String stt;
    private String name;
    private double price;
    private boolean state;

    // public OrderSeatModel(SeatHasCheckTicket seat){
    //     setStt(seat.getId());
    //     setName(seat.getName());
    //     setPrice(seat.getPriceCommon());
    //     setState(seat.isState());
    // }
}
