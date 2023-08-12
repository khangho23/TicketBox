package com.example.demo.embeddable;

import java.util.Date;

import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class ShowtimeEmbeddable {
	@Column(name = "SHOWTIME")
	final Date showtime;
	
	@Column(name = "SHOWDATE")
	final Date showdate;
}
