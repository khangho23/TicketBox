package com.example.demo.dao;

import com.example.demo.entity.PaymentDetails;

import java.util.Optional;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface PaymentDetailsDao {
    @Insert
    int insert(PaymentDetails paymentDetails);

    @Update(sqlFile = true)
    int updateStatusByBillId(int billId, int status);

    @Select
    PaymentDetails findByTransactionNo(String vnp_TransactionNo);

    @Select
    Optional<PaymentDetails> findByBillId(Integer billId);
}
