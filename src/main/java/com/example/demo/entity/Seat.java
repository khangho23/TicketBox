package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@Data
public class Seat {
    /*
     * id of seat
     */
    @Id
    @Column
    private String id;
    /*
     * id of Seat Type
     */
    @Column
    private int seatTypeId;
    /*
     * row of Seat
     */
    @Column
    private String rowSeat;
    /*
     * order of Seat
     */
    @Column
    private int orderSeat;
    /*
     * price of Seat
     */
    @Column
    private double priceCommon;
}
