package com.info.worldweather.impl;
import com.info.worldweather.model.CityWeather;
import com.info.worldweather.model.Weather;
import com.info.worldweather.model.WeatherLog;
import com.info.worldweather.repository.WeatherLogReposity;
import com.info.worldweather.constant.WeatherAPIConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WeatherAPIImpl {

	private final WeatherLogReposity weatherLogRepository;
	private final RestTemplate restTemplate;

	@Autowired
	WeatherAPIImpl(WeatherLogReposity weatherLogRepository, RestTemplate restTemplate){
		this.weatherLogRepository = weatherLogRepository;
		this.restTemplate = restTemplate;
	}

	CityWeather cityWeather;

	private static final Logger log = LoggerFactory.getLogger(WeatherAPIImpl.class);

	public List<CityWeather> getWeather(String appID,
            String cityName1,
            String cityName2,
            String cityName3,
            HttpServletRequest request) throws Exception {

		log.info(this.getClass().getSimpleName() + " INITIATED...");

		List<CityWeather> response = new ArrayList<CityWeather>();

		String[] cityName = {cityName1,cityName2,cityName3};

		// new `for` loop
        for (String s: cityName)
        {
        	if(s!=null){
        		response.add(getWeatherInfo(s, appID));
        	}
        }

		return response;

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<WeatherLog> getActualWeather(String appID,
            String cityName1,
            String cityName2,
            String cityName3,
            HttpServletRequest request) throws Exception {

		log.info(this.getClass().getSimpleName() + " INITIATED...");

		Date today = Calendar.getInstance().getTime();

		List<WeatherLog> response = new ArrayList<WeatherLog>();

		List<CityWeather> cityWeatherList = getWeather(appID, cityName1,cityName2,cityName3, request);

		log.info("cityWeatherList : " + cityWeatherList.toString());

		// new `for` loop
        for (CityWeather c: cityWeatherList)
        {
        	List<Weather> weatherList = new ArrayList<Weather>();
        	weatherList = c.getWeather();

        	log.info("weatherList : " + weatherList.iterator().toString());

        	for(Weather w: weatherList)
        	{
        		WeatherLog weatherLog = new WeatherLog();

        		weatherLog.setResponseId(Integer.toString(w.getId()));
        		weatherLog.setActualWeather(w.getDescription());
        		weatherLog.setLocation(c.getName());
        		weatherLog.setTemperature(Float.toString(c.getMain().getTemp()));

        		response.add(weatherLog);
        	}
        }

        //This is to save response into databse
        weatherLogRepository.saveAll(response);

		return response;

	}

	private CityWeather getWeatherInfo(String cityName, String appID) {

		cityWeather = restTemplate.getForObject(WeatherAPIConstants.WS_URL + "?q="+cityName+"&APPID="+appID, CityWeather.class);

		log.info("response object cityWeather: " + cityWeather.toString());

		return cityWeather;
	}
}
