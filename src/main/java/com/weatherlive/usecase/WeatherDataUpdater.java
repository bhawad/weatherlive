package com.weatherlive.usecase;

import com.weatherlive.entity.CityWeatherData;
import com.weatherlive.event.WeatherUpdateErrorEvent;
import com.weatherlive.event.WeatherUpdatedEvent;
import com.weatherlive.repository.CityWeatherDataRepository;
import com.weatherlive.repository.OpenWeatherFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeatherDataUpdater {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final OpenWeatherFeignClient openWeatherFeignClient;
    private final CityWeatherDataRepository cityWeatherDataRepository;

    @Value("${openweather.appId}")
    private String openWeatherAppId;
    @Value("${openweather.unit}")
    private String openWeatherUnit;
    @Value("${app.cities}")
    private List<String> cities;

    @Scheduled(fixedDelay = 5000)
    public void updateCitiesWeatherData() {
        log.debug("Starting cities update job");

        if (CollectionUtils.isEmpty(cities)) {
            log.debug("No Available cities found");
            return;
        }

        cities.stream().forEach(this::updateAndPublishCityData);
    }

    private void updateAndPublishCityData(String city) {
        try {
            log.debug("Loading weather updated details for city {}", city);
            CityWeatherData cityWeatherData = openWeatherFeignClient.findWeatherDataForCity(city, openWeatherUnit, openWeatherAppId);
            cityWeatherDataRepository.save(cityWeatherData);
            simpMessageSendingOperations.convertAndSend("/topic/weather.".concat(city), new WeatherUpdatedEvent(cityWeatherData));

        } catch (Exception ex) {
            log.error("Error updating weather details for city {}", city, ex);

            WeatherUpdateErrorEvent weatherUpdateErrorEvent = new WeatherUpdateErrorEvent(city);
            simpMessageSendingOperations.convertAndSend("/topic/weather.".concat(city), weatherUpdateErrorEvent);
        }
    }
}
