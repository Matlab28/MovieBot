package com.example.moviebot.dto.movieDtos.detailedResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductionCountryDto {
    @JsonProperty("iso_3166_1")
    public String iso31661;
    public String name;
}
