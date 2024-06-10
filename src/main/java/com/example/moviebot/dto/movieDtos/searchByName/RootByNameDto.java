package com.example.moviebot.dto.movieDtos.searchByName;

import com.example.moviebot.dto.movieDtos.serachByGenre.ResultByNameDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class RootByNameDto {
    public Integer page;
    public ArrayList<ResultByNameDto> results;
    @JsonProperty("total_pages")
    public Integer totalPages;
    @JsonProperty("total_results")
    public Integer totalResults;
}

