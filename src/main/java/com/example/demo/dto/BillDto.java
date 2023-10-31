package com.example.demo.dto;

import java.util.List;

import org.seasar.doma.Transient;

import com.example.demo.entity.Bill;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.ToppingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDto extends Bill {
	@Transient
	private List<Ticket> tickets;
	@Transient
	private List<ToppingDetails> toppingDetails;
}
