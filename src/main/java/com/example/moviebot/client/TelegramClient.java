package com.example.moviebot.client;


import com.example.moviebot.dto.request.RootRequestDto;
import com.example.moviebot.dto.request.TelegramSendDto;
import com.example.moviebot.dto.response.RootResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "telegramApi", url = "https://api.telegram.org/bot6683817416:AAEO2kOiB5YB-SaT60lpr5b5Z3tcmk3wgJE")
public interface TelegramClient {
    @GetMapping("/getUpdates?offset={value}")
    RootRequestDto getUpdates(@PathVariable Long value);

    @PostMapping("/sendMessage")
    RootResponseDto sendMessage(@RequestBody TelegramSendDto dto);
}
