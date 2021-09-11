package com.weatherlive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherLiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherLiveApplication.class, args);
    }

}
