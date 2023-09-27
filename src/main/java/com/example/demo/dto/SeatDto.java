package com.example.demo.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Seat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class SeatDto extends Seat {
    /*
     * stt of seat
     */
    @Column
    private int stt;
    
    @Column
    private String name;
    /*
     * roomId of seat
     */
    @Column
    private String roomId;
    /*
     * price details of seat
     */
    @Column
    private double priceDetails;

    @Column
    private double total;
}
