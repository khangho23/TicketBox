package com.example.demo.dto;

import com.example.demo.entity.Bill;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

import java.util.Date;

@Data
@Entity
public class BillHistory extends Bill {
    @Column(name = "ticket")
    private Integer ticket;
    @Column(name = "starttime")
    private Date startTime;
    @Column(name = "showdate")
    private Date showDate;
    @Column(name = "movie")
    private String movieName;
    @Column(name = "time")
    private Date movieTime;
    @Column(name = "room")
    private String roomName;
    @Column(name = "branch")
    private String branchName;
    @Column(name = "address")
    private String address;
    @Column(name = "seat")
    private String seat;
}
