package com.example.demo.model;

import java.util.List;

import com.example.demo.dto.MovieDto;
import com.example.demo.dto.PosterDto;
import com.example.demo.dto.ReviewDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieDetailModel {
    private MovieDto movieDetail;
    private List<PosterDto> listTypeOfMovies;
    private List<ReviewDto> listReview;
}
