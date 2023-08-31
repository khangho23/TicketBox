package com.example.demo.dto;

import java.sql.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import com.example.demo.entity.Showtime;

import lombok.Data;

@Data
@Entity
public class ShowtimeDto extends Showtime{
	@Column(name = "room")
    private String roomName;
    @Column(name = "dimension")
    private String dimensionName;
    @Column(name = "branch")
    private String branchName;
    @Column(name = "branchAddress")
    private String branchAddress;
}
