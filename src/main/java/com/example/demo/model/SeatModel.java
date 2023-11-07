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
            Integer seatDetailsId = Integer.parseInt(obj[0]);
            String name = obj[1];
            boolean isBooked = Boolean.parseBoolean(obj[2]);
            seats.add(new SeatHasCheck(seatDetailsId, name, isBooked));
        }
        seats = seats.stream()
                        .sorted((a,b)->
                            Integer.valueOf(a.getName().substring(1))
                            .compareTo(Integer.valueOf(b.getName().substring(1))))
                        .toList();
    }

}
@Data
@NoArgsConstructor
@AllArgsConstructor
class SeatHasCheck {
	private Integer seatDetailsId;
    private String name;
    private boolean isBooked;
}