package com.example.demo.dao;

import com.example.demo.entity.TokenVnpay;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface TokenVnpayDao {
	@Insert
	int insert(TokenVnpay tokenVnpay);

	@Select
	TokenVnpay findByCustomerId(Integer customerId);
}
