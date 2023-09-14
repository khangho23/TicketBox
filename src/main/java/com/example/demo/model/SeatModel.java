package com.example.demo.model;

import java.util.List;

import com.example.demo.dto.SeatDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatModel {
    private List<RowSeatModel> rows;
    private String roomId;

    // public SeatModel(List<SeatDto> seatDto) {
    //     setRoomId(seatDto.get(0).getRoomId());
    //     seatDto.stream().map(s->new RowSeatModel(s.getRowSeat(),s));
    // }
}
