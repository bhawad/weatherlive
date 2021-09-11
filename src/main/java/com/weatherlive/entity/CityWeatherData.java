package com.weatherlive.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@RedisHash("weatherData")
public class CityWeatherData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;
    private List<WeatherInfo> weather = new ArrayList<>();
    private MainInfo main;
    private WindInfo wind;
    private CloudsInfo clouds;
    private Long dt;
}
