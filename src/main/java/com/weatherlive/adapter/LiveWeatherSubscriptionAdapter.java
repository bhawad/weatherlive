package com.weatherlive.adapter;

import com.weatherlive.event.IEvent;
import com.weatherlive.event.WeatherUpdatedEvent;
import com.weatherlive.repository.CityWeatherDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LiveWeatherSubscriptionAdapter {

    private final CityWeatherDataRepository cityWeatherDataRepository;

    @SubscribeMapping("/weather.{city}")
    public IEvent subscribeToCityWeatherData(@DestinationVariable("city") String city) {
        log.debug("Subscribing to city weather data for city {}", city);
        return cityWeatherDataRepository.findById(city).map(c -> new WeatherUpdatedEvent(c)).orElse(null);
    }

}
