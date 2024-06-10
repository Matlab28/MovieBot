package com.example.moviebot.dto.movieDtos.serachByGenre;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class RootByGenreDto {
    public Integer page;
    public ArrayList<ResultByGenreDto> results;
    @JsonProperty("total_pages")
    public Integer totalPages;
    @JsonProperty("total_results")
    public Integer totalResults;
}
