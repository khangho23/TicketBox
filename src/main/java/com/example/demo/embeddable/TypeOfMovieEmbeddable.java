package com.example.demo.embeddable;

import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class TypeOfMovieEmbeddable {
	@Column(name = "MOVIE_TYPES")
	final String movieTypeName;
}
