package com.example.demo.embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
public class CountryEmbeddable {
	@Column(name = "COUNTRY")
	final String countryName;
}
