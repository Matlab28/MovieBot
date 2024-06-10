package com.example.moviebot.dto.movieDtos.genreList;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class RootGenreListDto {
    private ArrayList<GenreDto> genres;
}
