package com.example.moviebot.client;

import com.example.moviebot.dto.movieDtos.serachByGenre.RootByGenreDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchByGenre", url = "https://advanced-movie-search.p.rapidapi.com/discover/movie?with_genres=80&page=1")
public interface SearchByGenreAPIClient {
    @GetMapping
    RootByGenreDto getData(@RequestParam("with_genres") String genre,
                           @RequestParam("page") String page,
                           @RequestHeader("x-rapidapi-host") String host,
                           @RequestHeader("x-rapidapi-key") String apiKey);
}
