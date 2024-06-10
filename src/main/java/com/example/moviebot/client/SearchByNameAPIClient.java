package com.example.moviebot.client;

import com.example.moviebot.dto.movieDtos.searchByName.RootByNameDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "searchByName", url = "https://advanced-movie-search.p.rapidapi.com/search/movie?query=kong&page=1")
public interface SearchByNameAPIClient {
    @GetMapping
    RootByNameDto getData(@RequestParam("query") String query,
                          @RequestParam("page") String page,
                          @RequestHeader("x-rapidapi-host") String host,
                          @RequestHeader("x-rapidapi-key") String apiKey);
}
