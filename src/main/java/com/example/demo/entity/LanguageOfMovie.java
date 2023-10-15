package com.example.demo.entity;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
public class LanguageOfMovie {
	@Column
	private Integer id;
	@Column
	private String movieId;
	@Column
	private int languageId;
}
