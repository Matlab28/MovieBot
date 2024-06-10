package com.example.moviebot.client;

import com.example.moviebot.dto.movieDtos.genreList.RootGenreListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "genreList", url = "https://advanced-movie-search.p.rapidapi.com/genre/movie/list")
public interface GenreListAPIClient {
    @GetMapping
    RootGenreListDto getData(@RequestHeader("x-rapidapi-host") String host,
                             @RequestHeader("x-rapidapi-key") String apiKey);
}
