package com.example.demo.dao;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.dto.TicketDto;
import com.example.demo.entity.Ticket;

@Dao
@ConfigAutowireable
public interface TicketDao {
	@Select
	List<Ticket> findAll();
	
	@Select
	List<TicketDto> findByCustomerId(Integer customerId);

	@Select
	List<TicketDto> findByBillId(Integer billId);

	@Insert
	int insert(Ticket ticket);
}
