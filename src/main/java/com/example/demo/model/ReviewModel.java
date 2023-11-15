package com.example.demo.model;

import java.util.List;

import com.example.demo.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewModel {
    private List<ReviewDto> review;
    private Integer total_bill_count;
}
