package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.SeatHasCheckTicket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RowSeatModel {
    private String row;
    private List<OrderSeatModel> seats = new ArrayList<>();

    // public RowSeatModel(String row, List<SeatHasCheckTicket> seats) {
    //     setRow(row);
    //     seats.stream().filter(s -> s.getRowSeat().equals(row))
    //                     .map(s -> new OrderSeatModel(s))
    //                     .forEach(s->this.seats.add(s));
    // }
}
