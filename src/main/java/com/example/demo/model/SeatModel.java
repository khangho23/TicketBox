package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.SeatHasCheckTicket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SeatModel {
    private String row;
    private List<SeatHasCheck> seats;

    public SeatModel(SeatHasCheckTicket seatHasCheckTicket){
        this.row = seatHasCheckTicket.getRow();
        String[] arr = seatHasCheckTicket.getSeats().split(",");
        seats = new ArrayList<>();
        for(int i = 0 ; i < arr.length ; i++){
            String[] obj = arr[i].split(":");
            String name = obj[0];
            boolean isBooked = Boolean.parseBoolean(obj[1]);
            seats.add(new SeatHasCheck(name,isBooked));
        }
    }

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class SeatHasCheck {
    private String name;
    private boolean isBooked;
}