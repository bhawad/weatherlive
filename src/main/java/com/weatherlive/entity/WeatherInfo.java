package com.weatherlive.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WeatherInfo implements Serializable {

    private Long id;
    private String main;
    private String description;
    private String icon;
}
