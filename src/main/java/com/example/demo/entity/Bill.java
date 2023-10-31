package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.GeneratedValue;
import org.seasar.doma.GenerationType;
import org.seasar.doma.Id;
import org.seasar.doma.SequenceGenerator;
import org.seasar.doma.Table;

import java.sql.Date;

@Entity
@Data
@Table
@NoArgsConstructor
public class Bill {
    /*
     *	Id of bill
     */
    @Id
    @SequenceGenerator(sequence = "bill_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column
    private Integer id;
    /*
     *	total price of bill
     */
    @Column(name = "totalprice")
    private double totalPrice;
    /*
     *	export date of bill
     */
    @Column
    private Date exportDate;
    /*
     *	status of bill
     */
    @Column(name = "exportstatus")
    private Integer exportStatus;
    /*
     *	status of bill
     */
    @Column(name = "qrcode")
    private String qrCode;
}
