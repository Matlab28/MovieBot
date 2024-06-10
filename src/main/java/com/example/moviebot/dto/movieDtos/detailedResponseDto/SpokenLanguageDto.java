package com.example.moviebot.dto.movieDtos.detailedResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpokenLanguageDto {
    @JsonProperty("english_name")
    public String englishName;
    @JsonProperty("iso_639_1")
    public String iso6391;
    public String name;
}
