package com.example.demo.dto;

import java.util.List;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;
import org.seasar.doma.Transient;

import com.example.demo.entity.Movie;
import com.example.demo.entity.MovieConfig;

import lombok.Data;
@Entity
@Data
public class MovieConfigDto extends Movie {
	
	  @Transient
	   private List<MovieConfig> listMovieConfig;
}
