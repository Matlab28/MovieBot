package com.example.moviebot.controller;

import com.example.moviebot.dto.response.RootResponseDto;
import com.example.moviebot.service.MovieService;
import com.example.moviebot.service.MovieService2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
    private final MovieService service;
    private final MovieService2 service2;

//    @GetMapping
//    public RootResponseDto response() {
//        return service.response();
//    }

    @GetMapping
    public RootResponseDto response() {
        return service2.response();
    }
}
