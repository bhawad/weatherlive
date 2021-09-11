package com.weatherlive.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MainInfo implements Serializable {

    private Float temp;
    @JsonProperty("feels_like")
    private Float feelsLike;
    @JsonProperty("temp_min")
    private Float tempMin;
    @JsonProperty("temp_max")
    private Float tempMax;
    private Integer pressure;
    private Integer humidity;
}
