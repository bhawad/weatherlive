package com.weatherlive.adapter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/city")
@Slf4j
public class CitiesAdapter {

    @Value("${app.cities}")
    private List<String> cities;

    @GetMapping
    private List<String> getAvailableCities() {
        log.debug("Request to load available cities");
        return cities;
    }
}
