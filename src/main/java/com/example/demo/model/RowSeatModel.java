package com.example.demo.model;

import java.util.Arrays;
import java.util.List;

import com.example.demo.dto.SeatDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RowSeatModel {
    private String key;
    private List<OrderSeatModel> orders;
    public static RowSeatModel init (SeatDto a,SeatDto b) {
        RowSeatModel seat = new RowSeatModel();
        if(a.getRowSeat().equals(b.getRowSeat())){
            seat.setKey(a.getRowSeat());
            seat.setOrders(Arrays.asList(
                    new OrderSeatModel(a.getId(),a.getOrderSeat(),a.getPriceCommon(),a.getPriceDetails()),
                    new OrderSeatModel(b.getId(),b.getOrderSeat(),b.getPriceCommon(),b.getPriceDetails())
            ));
            return seat;
        }

        return null;
    }
}
