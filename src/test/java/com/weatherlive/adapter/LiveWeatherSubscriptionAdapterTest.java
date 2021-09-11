package com.weatherlive.adapter;

import com.weatherlive.entity.CityWeatherData;
import com.weatherlive.event.IEvent;
import com.weatherlive.event.WeatherUpdatedEvent;
import com.weatherlive.repository.CityWeatherDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

@SpringBootTest
@TestPropertySource(properties = {
        "app.scheduling.enable=false",
        "app.cities=Berlin"
})
class LiveWeatherSubscriptionAdapterTest {

    @MockBean
    CityWeatherDataRepository cityWeatherDataRepository;

    @Autowired
    LiveWeatherSubscriptionAdapter liveWeatherSubscriptionAdapter;

    @Test
    public void testCityWeatherSubscriptionExists() {

        //setup
        CityWeatherData cityWeatherData = Mockito.mock(CityWeatherData.class);
        WeatherUpdatedEvent event = new WeatherUpdatedEvent(cityWeatherData);
        Mockito.when(cityWeatherDataRepository.findById(Mockito.eq("Berlin"))).thenReturn(Optional.of(cityWeatherData));

        //execute
        IEvent result = liveWeatherSubscriptionAdapter.subscribeToCityWeatherData("Berlin");

        //assert & verify
        Assertions.assertNotNull(result, "Event result must not be null");
        Assertions.assertEquals(event, result, "Weather updated event result must be equal");
        Mockito.verify(cityWeatherDataRepository, Mockito.times(1)).findById(Mockito.eq("Berlin"));
    }

    @Test
    public void testCityWeatherSubscriptionNotExists() {

        //setup
        Mockito.when(cityWeatherDataRepository.findById(Mockito.eq("Berlin"))).thenReturn(Optional.empty());

        //execute
        IEvent result = liveWeatherSubscriptionAdapter.subscribeToCityWeatherData("Berlin");

        //assert & verify
        Assertions.assertNull(result, "Event result be null");
        Mockito.verify(cityWeatherDataRepository, Mockito.times(1)).findById(Mockito.eq("Berlin"));
    }

}