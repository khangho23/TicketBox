package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

import com.example.demo.entity.Customer;

@Dao
@ConfigAutowireable
public interface CustomerDao {
	@Select
	List<Customer> findAll();

	@Select
	Optional<Customer> findByEmail(String email);

	@Select
	Optional<Customer> findById(Integer id);

	@Select
	public Customer findByKey(String email, String password);

	@Insert(exclude = { "id", "address" })
	int insert(Customer customer);

	@Delete
	int delete(Customer customer);

	@Update
	int update(Customer customer);

	@Select
	List<Customer> findByActiveAndCreatedAtBefore(boolean active, LocalDateTime created_at);
}
