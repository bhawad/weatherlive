package com.weatherlive.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherUpdateErrorEvent implements IEvent {

    private String cityName;

    @Override
    public EventType getType() {
        return EventType.WEATHER_UPDATE_ERROR;
    }
}
