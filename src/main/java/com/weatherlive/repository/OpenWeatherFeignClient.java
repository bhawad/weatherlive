package com.weatherlive.repository;

import com.weatherlive.entity.CityWeatherData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "openWeatherClient", url = "https://api.openweathermap.org/data/2.5")
public interface OpenWeatherFeignClient {

    @GetMapping("/weather")
    CityWeatherData findWeatherDataForCity(
            @RequestParam("q") String cityName,
            @RequestParam("units") String units,
            @RequestParam("appid") String appId
    );
}
