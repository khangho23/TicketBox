package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfMovie {
    @Id
    @Column
    private String id;

    @Column
    private String name;
}
