package com.example.moviebot.dto.movieDtos.detailedResponseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailedRootResponseDto {
    private Boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("belongs_to_collection")
    private BelongsToCollectionDto belongsToCollection;
    private Integer budget;
    private ArrayList<GenreResponseDto> genres;
    private String homepage;
    private Integer id;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("origin_country")
    private ArrayList<String> originCountry;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    private Double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("production_companies")
    private ArrayList<ProductionCompanyDto> productionCompanies;
    @JsonProperty("production_countries")
    private ArrayList<ProductionCountryDto> productionCountries;
    @JsonProperty("release_date")
    private String releaseDate;
    private Integer revenue;
    private Integer runtime;
    @JsonProperty("spoken_languages")
    private ArrayList<SpokenLanguageDto> spokenLanguages;
    private String status;
    private String tagline;
    private String title;
    private Boolean video;
    @JsonProperty("vote_average")
    private Double voteAverage;
    @JsonProperty("vote_count")
    private Integer voteCount;
}
