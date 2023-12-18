package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BillHistoriesDto {
    private List<BillHistoryDto> bills;
    private Integer total_bill_count;
}

