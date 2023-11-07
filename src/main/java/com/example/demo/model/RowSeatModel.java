package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RowSeatModel {
    private String row;
    private List<OrderSeatModel> seats = new ArrayList<>();
}
