package com.weatherlive.event;

import com.weatherlive.entity.CityWeatherData;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WeatherUpdatedEvent implements IEvent {

    private CityWeatherData cityWeatherData;

    @Override
    public EventType getType() {
        return EventType.WEATHER_UPDATED;
    }
}
