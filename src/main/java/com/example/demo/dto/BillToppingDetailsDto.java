package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.ToppingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillToppingDetailsDto {
	private Integer billId;
	private List<ToppingDetails> toppingDetails;
}
