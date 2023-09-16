package com.example.demo.dto;


import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import com.example.demo.entity.Seat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class SeatHasCheckTicket extends Seat{
    /*
     * name of seat
     */
    @Column
    private String name;
    /*
     * id of ticket
     */
    @Column
    private String ticketId;
}
