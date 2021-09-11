package com.weatherlive.repository;

import com.weatherlive.entity.CityWeatherData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityWeatherDataRepository extends CrudRepository<CityWeatherData, String> {
}
