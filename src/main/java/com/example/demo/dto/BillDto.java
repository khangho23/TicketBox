package com.example.demo.dto;

import java.util.List;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {
	private Bill bill;
	private List<Ticket> tickets;
}
