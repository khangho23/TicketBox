package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.*;

@Entity
@Table(name = "tokenvnpay")
@Data
@NoArgsConstructor
public class TokenVnpay {
    @Id
    @SequenceGenerator(sequence = "tokenvnpay_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column
    private Integer id;

    @Column(name = "customerid")
    private Integer vnp_app_user_id;

    @Column
    private String vnp_token;

    @Column
    private String vnp_card_number;
    
    @Column
    private String vnp_card_type;
    
    @Column
    private String vnp_bank_code;
    
    @Column
    private String vnp_create_date;
}
