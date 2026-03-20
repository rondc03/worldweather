package com.info.worldweather.controller;

import com.info.worldweather.impl.WeatherAPIImpl;
import com.info.worldweather.model.CityWeather;
import com.info.worldweather.model.WeatherLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class WeatherAPI {

    private static final Logger log = LoggerFactory.getLogger(WeatherAPI.class);

    @Autowired
    private WeatherAPIImpl weatherImpl;

    @GetMapping("/weather")
    public List<CityWeather> getWeather(
            @RequestParam String appID,
            @RequestParam(required = false) String cityName1,
            @RequestParam(required = false) String cityName2,
            @RequestParam(required = false) String cityName3,
            HttpServletRequest request) throws Exception {
        log.info("getWeather called");
        return weatherImpl.getWeather(appID, cityName1, cityName2, cityName3, request);
    }

    @GetMapping("/actualweather")
    public List<WeatherLog> getActualWeather(
            @RequestParam String appID,
            @RequestParam(required = false) String cityName1,
            @RequestParam(required = false) String cityName2,
            @RequestParam(required = false) String cityName3,
            HttpServletRequest request) throws Exception {
        log.info("getActualWeather called");
        return weatherImpl.getActualWeather(appID, cityName1, cityName2, cityName3, request);
    }
}
