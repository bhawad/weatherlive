package com.weatherlive.usecase;

import com.weatherlive.entity.*;
import com.weatherlive.event.WeatherUpdateErrorEvent;
import com.weatherlive.event.WeatherUpdatedEvent;
import com.weatherlive.repository.CityWeatherDataRepository;
import com.weatherlive.repository.OpenWeatherFeignClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

@SpringBootTest
@TestPropertySource(properties = {
        "app.scheduling.enable=false",
        "app.cities = Berlin"
})
class WeatherDataUpdaterTest {

    @MockBean
    private SimpMessagingTemplate simpMessageSendingOperations;
    @MockBean
    private OpenWeatherFeignClient openWeatherFeignClient;
    @MockBean
    private CityWeatherDataRepository cityWeatherDataRepository;

    @Value("${openweather.appId}")
    private String openWeatherAppId;
    @Value("${openweather.unit}")
    private String openWeatherUnit;

    @Autowired
    private WeatherDataUpdater weatherDataUpdater;

    @Test
    public void testUpdaterSuccess() {

        //setup
        CityWeatherData cityWeatherResponse = buildWeatherData();
        Mockito.when(openWeatherFeignClient.findWeatherDataForCity(
                Mockito.eq("Berlin"),
                Mockito.eq(openWeatherUnit),
                Mockito.eq(openWeatherAppId)
        )).thenReturn(cityWeatherResponse);
        Mockito.doNothing().when(simpMessageSendingOperations).convertAndSend(Mockito.eq("/topic/weather.Berlin"),
                Mockito.eq(new WeatherUpdatedEvent(cityWeatherResponse)));
        Mockito.when(cityWeatherDataRepository.save(cityWeatherResponse)).thenReturn(null);

        //execute
        weatherDataUpdater.updateCitiesWeatherData();

        //verify and assert
        Mockito.verify(openWeatherFeignClient, Mockito.times(1)).findWeatherDataForCity(
                Mockito.eq("Berlin"),
                Mockito.eq(openWeatherUnit),
                Mockito.eq(openWeatherAppId));
        Mockito.verify(simpMessageSendingOperations, Mockito.times(1)).convertAndSend(Mockito.eq("/topic/weather.Berlin"),
                Mockito.eq(new WeatherUpdatedEvent(cityWeatherResponse)));
        Mockito.verify(cityWeatherDataRepository, Mockito.times(1)).save(cityWeatherResponse);

    }

    @Test
    public void testUpdaterException() {
        //setup
        WeatherUpdateErrorEvent weatherUpdateErrorEvent = new WeatherUpdateErrorEvent("Berlin");
        Mockito.when(openWeatherFeignClient.findWeatherDataForCity(
                Mockito.eq("Berlin"),
                Mockito.eq(openWeatherUnit),
                Mockito.eq(openWeatherAppId)
        )).thenThrow(new RuntimeException());
        Mockito.doNothing().when(simpMessageSendingOperations).convertAndSend(Mockito.eq("/topic/weather.Berlin"),
                Mockito.eq(weatherUpdateErrorEvent));

        //execute
        weatherDataUpdater.updateCitiesWeatherData();

        //verify and assert
        Mockito.verify(openWeatherFeignClient, Mockito.times(1)).findWeatherDataForCity(
                Mockito.eq("Berlin"),
                Mockito.eq(openWeatherUnit),
                Mockito.eq(openWeatherAppId));
        Mockito.verify(simpMessageSendingOperations, Mockito.times(1))
                .convertAndSend(Mockito.eq("/topic/weather.Berlin"), Mockito.eq(weatherUpdateErrorEvent));
        Mockito.verify(cityWeatherDataRepository, Mockito.never()).save(Mockito.any());
    }


    private CityWeatherData buildWeatherData() {

        CityWeatherData cityWeatherData = new CityWeatherData();
        cityWeatherData.setDt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        cityWeatherData.setName("Berlin");

        //clouds
        CloudsInfo cloudsInfo = new CloudsInfo();
        cloudsInfo.setAll(90);
        cityWeatherData.setClouds(cloudsInfo);

        //main info
        MainInfo mainInfo = new MainInfo();
        mainInfo.setHumidity(80);
        mainInfo.setPressure(89);
        mainInfo.setTemp(78.4f);
        mainInfo.setTempMax(99.3f);
        mainInfo.setFeelsLike(33.6f);
        cityWeatherData.setMain(mainInfo);

        //wind info
        WindInfo windInfo = new WindInfo();
        windInfo.setSpeed(55.4f);
        cityWeatherData.setWind(windInfo);

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setDescription("few clouds");
        weatherInfo.setIcon("10nd");
        weatherInfo.setId(500l);
        weatherInfo.setMain("Clouds");
        cityWeatherData.setWeather(Collections.singletonList(weatherInfo));

        return cityWeatherData;
    }

}