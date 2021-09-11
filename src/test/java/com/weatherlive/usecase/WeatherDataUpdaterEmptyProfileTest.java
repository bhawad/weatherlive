package com.weatherlive.usecase;

import com.weatherlive.repository.CityWeatherDataRepository;
import com.weatherlive.repository.OpenWeatherFeignClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "app.scheduling.enable=false",
        "app.cities = "
})
class WeatherDataUpdaterEmptyProfileTest {

    @MockBean
    private SimpMessagingTemplate simpMessageSendingOperations;
    @MockBean
    private OpenWeatherFeignClient openWeatherFeignClient;
    @MockBean
    private CityWeatherDataRepository cityWeatherDataRepository;

    @Autowired
    private WeatherDataUpdater weatherDataUpdater;

    @Test
    public void testUpdaterNoData() {

        //execute
        weatherDataUpdater.updateCitiesWeatherData();

        //verify and assert
        Mockito.verify(openWeatherFeignClient, Mockito.never()).findWeatherDataForCity(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.verify(simpMessageSendingOperations, Mockito.never()).convertAndSend(Mockito.any(String.class), Mockito.any(Object.class));
        Mockito.verify(cityWeatherDataRepository, Mockito.never()).save(Mockito.any());
    }

}