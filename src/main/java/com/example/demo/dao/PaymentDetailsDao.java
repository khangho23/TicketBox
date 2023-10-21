package com.example.demo.dao;

import com.example.demo.entity.PaymentDetails;
import com.example.demo.entity.PaymentMethod;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface PaymentDetailsDao {
    @Insert
    int insert(PaymentDetails paymentDetails);

    @Update(include = {"status"})
    int updateStatus(PaymentDetails paymentDetails);
}
