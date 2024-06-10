package com.example.moviebot.service;

import com.example.moviebot.client.*;
import com.example.moviebot.dto.movieDtos.detailedResponseDto.*;
import com.example.moviebot.dto.movieDtos.genreList.RootGenreListDto;
import com.example.moviebot.dto.movieDtos.searchByName.RootByNameDto;
import com.example.moviebot.dto.movieDtos.serachByGenre.ResultByGenreDto;
import com.example.moviebot.dto.movieDtos.serachByGenre.ResultByNameDto;
import com.example.moviebot.dto.movieDtos.serachByGenre.RootByGenreDto;
import com.example.moviebot.dto.request.RootRequestDto;
import com.example.moviebot.dto.request.TelegramSendDto;
import com.example.moviebot.dto.response.RootResponseDto;
import feign.FeignException;
import feign.RetryableException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@Setter
@Getter
@RequiredArgsConstructor
public class MovieService2 {
    private final TelegramClient telegramClient;
    private final GenreListAPIClient genreListAPIClient;
    private final DetailedResponseAPIClient detailedResponseAPIClient;
    private final SearchByNameAPIClient searchByNameAPIClient;
    private final SearchByGenreAPIClient searchByGenreAPIClient;
    private final String host = "advanced-movie-search.p.rapidapi.com";
    private final String key = "674678e122mshd00ec5b8f945302p1052bcjsn0ad69ed2af91";
    private Random random = new Random();
    private Long lastUpdateId = 0L;

    public RootRequestDto getUpdateService() {
        RootRequestDto updates = telegramClient.getUpdates(0L);
        if (updates != null && !updates.getResult().isEmpty()) {
            Integer updateId = updates.getResult().get(updates.getResult().size() - 1).getUpdateId();
            log.info("Message got from - {}, ID - {}",
                    updates.getResult().get(0).getMessage().getFrom().getFirstName(),
                    updates.getResult().get(0).getMessage().getChat().getId());
            return telegramClient.getUpdates(Long.valueOf(updateId));
        }
        return null;
    }

    public RootResponseDto sendMessage(TelegramSendDto dto) {
        return telegramClient.sendMessage(dto);
    }

    public DetailedRootResponseDto detailedResponse() {
        return detailedResponseAPIClient.getData(host, key);
    }

    public RootGenreListDto genreList() {
        return genreListAPIClient.getData(host, key);
    }

    public RootByNameDto rootByName(String query) {
        int retryCount = 0;
        int maxRetries = 5;
        long waitTime = 1000;

        while (retryCount < maxRetries) {
            try {
                return searchByNameAPIClient.getData(query, "1", host, key);
            } catch (FeignException.TooManyRequests e) {
                retryCount++;
                log.warn("Rate limit exceeded. Retrying in {} ms. Attempt: {}", waitTime, retryCount);
                try {
                    TimeUnit.MILLISECONDS.sleep(waitTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during wait", ie);
                }
                waitTime *= 2;
            } catch (FeignException.BadRequest e) {
                log.error("Bad request: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("An error occurred while fetching movie by name: {}", e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Max retries reached for rootByName");
    }

    public RootByGenreDto rootByGenre() {
        int retryCount = 0;
        int maxRetries = 5;
        long waitTime = 1000;

        while (retryCount < maxRetries) {
            try {
                return searchByGenreAPIClient.getData("80", "1", host, key);
            } catch (FeignException.TooManyRequests e) {
                retryCount++;
                log.warn("Rate limit exceeded. Retrying in {} ms. Attempt: {}", waitTime, retryCount);
                try {
                    TimeUnit.MILLISECONDS.sleep(waitTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during wait", ie);
                }
                waitTime *= 2;
            } catch (FeignException.BadRequest e) {
                log.error("Bad request: {}", e.getMessage());
                throw e;
            } catch (RetryableException e) {
                log.warn("Retrying request due to: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("An error occurred while fetching movie by genre: {}", e.getMessage());
                throw e;
            }
        }
        throw new RuntimeException("Max retries reached for rootByGenre");
    }

    public String formatDetailedRoot(DetailedRootResponseDto dto) {
        String genres = dto.getGenres().stream()
                .map(GenreResponseDto::getName)
                .collect(Collectors.joining(", "));

        String originCountries = String.join(", ", dto.getOriginCountry());

        String spokenLanguages = dto.getSpokenLanguages().stream()
                .map(SpokenLanguageDto::getName)
                .collect(Collectors.joining(", "));

        String productionCountries = dto.getProductionCountries().stream()
                .map(ProductionCountryDto::getName)
                .collect(Collectors.joining(", "));

        String productionCompanies = dto.getProductionCompanies().stream()
                .map(ProductionCompanyDto::getName)
                .collect(Collectors.joining(", "));

        return String.format("Formatted detailed information:\n\n\n"
                        + " - Title : %s\n\n"
                        + " - Genre : %s\n\n"
                        + " - Origin country : %s\n\n"
                        + " - Spoken languages : %s\n\n"
                        + " - Is it for adults : %s\n\n"
                        + " - Overview : %s\n\n"
                        + " - Status : %s\n\n"
                        + " - Original language : %s\n\n"
                        + " - Budget : %s\n\n"
                        + " - Production countries : %s\n\n"
                        + " - Production companies : %s\n\n"
                        + " - Home page : %s\n\n",

                dto.getTitle(),
                genres,
                originCountries,
                spokenLanguages,
                dto.getAdult(),
                dto.getOverview(),
                dto.getStatus(),
                dto.getOriginalLanguage(),
                dto.getBudget(),
                productionCountries,
                productionCompanies,
                dto.getHomepage());
    }

    private String formatMovieDetails(Object movie) {
        if (movie instanceof ResultByNameDto) {
            ResultByNameDto movieByName = (ResultByNameDto) movie;
            return String.format("Title: %s\nOverview: %s\nRelease Date: %s\n\n",
                    movieByName.getTitle(), movieByName.getOverview(), movieByName.getReleaseDate());
        } else if (movie instanceof ResultByGenreDto) {
            ResultByGenreDto movieByGenre = (ResultByGenreDto) movie;
            return String.format("Title: %s\nOverview: %s\nRelease Date: %s\n\n",
                    movieByGenre.getTitle(), movieByGenre.getOverview(), movieByGenre.getReleaseDate());
        }
        return "";
    }

    public RootResponseDto response() {
        RootRequestDto updateService = getUpdateService();
        if (updateService != null && !updateService.getResult().isEmpty()) {
            String text = updateService.getResult().get(0).getMessage().getText();
            Long id = updateService.getResult().get(0).getMessage().getChat().getId();
            TelegramSendDto dto = new TelegramSendDto();
            dto.setChatId(String.valueOf(id));

            if ("/start".equals(text)) {
                String msg = "Hi " + updateService.getResult().get(0).getMessage().getFrom().getFirstName() +
                        ", welcome to movie recommender🍿";
                dto.setText(msg);
                return sendMessage(dto);
            }

            if (text.toLowerCase().contains("movie")) {
                DetailedRootResponseDto detailedInfo = detailedResponse();
                dto.setText(formatDetailedRoot(detailedInfo));
                return sendMessage(dto);
            }

            RootByNameDto titleResults = rootByName(text);
            for (ResultByNameDto nameDto : titleResults.getResults()) {
                if (text.toLowerCase().contains(nameDto.getTitle().toLowerCase())) {
                    dto.setText(formatResults(titleResults));
                    return sendMessage(dto);
                }
            }

            RootByGenreDto genreResults = rootByGenre();
            for (ResultByGenreDto genreDto : genreResults.getResults()) {
                if (text.toLowerCase().contains(genreDto.getTitle().toLowerCase())) {
                    dto.setText(formatMovieDetails(genreDto));
                    return sendMessage(dto);
                }
            }

            String randomMovie = getRandomMovie(titleResults, genreResults);
            dto.setText(randomMovie);
            return sendMessage(dto);
        }
        return null;
    }

    public String formatResults(RootByNameDto dto) {
        StringBuilder formattedResults = new StringBuilder();
        for (ResultByNameDto result : dto.getResults()) {
            formattedResults.append(String.format("Title: %s\n", result.getTitle()));
            formattedResults.append(String.format("Overview: %s\n", result.getOverview()));
            formattedResults.append(String.format("Release Date: %s\n", result.getReleaseDate()));
            formattedResults.append("\n");
        }
        return formattedResults.toString();
    }

    private String getRandomMovie(RootByNameDto titleResults, RootByGenreDto genreResults) {
        List<Object> combinedResults = new ArrayList<>();
        combinedResults.addAll(titleResults.getResults());
        combinedResults.addAll(genreResults.getResults());

        if (combinedResults.isEmpty()) {
            return "No movies found.";
        }

        Collections.shuffle(combinedResults);
        Object randomMovie = combinedResults.get(random.nextInt(combinedResults.size()));
        return formatMovieDetails(randomMovie);
    }

    @Scheduled(fixedDelay = 1000)
    public void refresh() {
        RootRequestDto updateService = getUpdateService();
        if (updateService != null && !updateService.getResult().isEmpty()) {
            Integer latestUpdateId = updateService.getResult().get(updateService.getResult().size() - 1).getUpdateId();
            if (latestUpdateId > lastUpdateId) {
                lastUpdateId = Long.valueOf(latestUpdateId);
                response();
            }
        }
    }
}
