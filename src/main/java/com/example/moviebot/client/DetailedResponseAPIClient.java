package com.example.moviebot.client;

import com.example.moviebot.dto.movieDtos.detailedResponseDto.DetailedRootResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "detailedResponse", url = "https://advanced-movie-search.p.rapidapi.com/movies/getdetails?movie_id=399566")
public interface DetailedResponseAPIClient {
    @GetMapping
    DetailedRootResponseDto getData(@RequestHeader("x-rapidapi-host") String host,
                                    @RequestHeader("x-rapidapi-key") String apiKey);
}
