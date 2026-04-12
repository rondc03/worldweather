package com.info.worldweather.impl;

import com.info.worldweather.constant.WeatherAPIConstants;
import com.info.worldweather.model.CityWeather;
import com.info.worldweather.model.Main;
import com.info.worldweather.model.Weather;
import com.info.worldweather.model.WeatherLog;
import com.info.worldweather.repository.WeatherLogReposity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherAPIImplTest {

    @Mock
    private WeatherLogReposity weatherLogReposity;

    @Mock
    private RestTemplate restTemplate;

    private WeatherAPIImpl weatherAPIImpl;

    private MockHttpServletRequest mockRequest;

    @Before
    public void setUp() {
        weatherAPIImpl = new WeatherAPIImpl(weatherLogReposity, restTemplate);
        mockRequest = new MockHttpServletRequest();
    }

    // --- helpers ---

    private CityWeather buildCityWeather(String name, float temp, int weatherId, String description) {
        Weather weather = new Weather();
        weather.setId(weatherId);
        weather.setDescription(description);

        Main main = new Main();
        main.setTemp(temp);

        CityWeather cityWeather = new CityWeather();
        cityWeather.setName(name);
        cityWeather.setMain(main);
        cityWeather.setWeather(Collections.singletonList(weather));
        return cityWeather;
    }

    // --- getWeather tests ---

    @Test
    public void testGetWeatherReturnsSingleCity() throws Exception {
        CityWeather london = buildCityWeather("London", 280.5f, 803, "broken clouds");
        when(restTemplate.getForObject(
                eq(WeatherAPIConstants.WS_URL + "?q=London&APPID=testKey"),
                eq(CityWeather.class))).thenReturn(london);

        List<CityWeather> result = weatherAPIImpl.getWeather("testKey", "London", null, null, mockRequest);

        assertEquals(1, result.size());
        assertEquals("London", result.get(0).getName());
    }

    @Test
    public void testGetWeatherReturnsMultipleCities() throws Exception {
        CityWeather london = buildCityWeather("London", 280f, 800, "clear sky");
        CityWeather paris  = buildCityWeather("Paris",  290f, 801, "few clouds");
        CityWeather tokyo  = buildCityWeather("Tokyo",  300f, 802, "scattered clouds");

        when(restTemplate.getForObject(contains("London"), eq(CityWeather.class))).thenReturn(london);
        when(restTemplate.getForObject(contains("Paris"),  eq(CityWeather.class))).thenReturn(paris);
        when(restTemplate.getForObject(contains("Tokyo"),  eq(CityWeather.class))).thenReturn(tokyo);

        List<CityWeather> result = weatherAPIImpl.getWeather("testKey", "London", "Paris", "Tokyo", mockRequest);

        assertEquals(3, result.size());
        assertEquals("London", result.get(0).getName());
        assertEquals("Paris",  result.get(1).getName());
        assertEquals("Tokyo",  result.get(2).getName());
    }

    @Test
    public void testGetWeatherSkipsNullCities() throws Exception {
        CityWeather london = buildCityWeather("London", 280f, 800, "clear sky");
        when(restTemplate.getForObject(contains("London"), eq(CityWeather.class))).thenReturn(london);

        List<CityWeather> result = weatherAPIImpl.getWeather("testKey", "London", null, null, mockRequest);

        assertEquals(1, result.size());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(CityWeather.class));
    }

    @Test
    public void testGetWeatherAllNullCitiesReturnsEmpty() throws Exception {
        List<CityWeather> result = weatherAPIImpl.getWeather("testKey", null, null, null, mockRequest);

        assertTrue(result.isEmpty());
        verify(restTemplate, never()).getForObject(anyString(), eq(CityWeather.class));
    }

    // --- getActualWeather tests ---

    @Test
    public void testGetActualWeatherTransformsDataCorrectly() throws Exception {
        CityWeather london = buildCityWeather("London", 275.88f, 804, "overcast clouds");
        when(restTemplate.getForObject(contains("London"), eq(CityWeather.class))).thenReturn(london);

        List<WeatherLog> result = weatherAPIImpl.getActualWeather("testKey", "London", null, null, mockRequest);

        assertEquals(1, result.size());
        WeatherLog log = result.get(0);
        assertEquals("London", log.getLocation());
        assertEquals("overcast clouds", log.getActualWeather());
        assertEquals("804", log.getResponseId());
        assertEquals("275.88", log.getTemperature());
    }

    @Test
    public void testGetActualWeatherSavesToRepository() throws Exception {
        CityWeather london = buildCityWeather("London", 275f, 800, "clear sky");
        when(restTemplate.getForObject(contains("London"), eq(CityWeather.class))).thenReturn(london);

        weatherAPIImpl.getActualWeather("testKey", "London", null, null, mockRequest);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<WeatherLog>> captor = ArgumentCaptor.forClass(List.class);
        verify(weatherLogReposity, times(1)).saveAll(captor.capture());
        assertEquals(1, captor.getValue().size());
    }

    @Test
    public void testGetActualWeatherSavesAllCities() throws Exception {
        CityWeather london = buildCityWeather("London", 280f, 800, "clear sky");
        CityWeather paris  = buildCityWeather("Paris",  290f, 801, "few clouds");

        when(restTemplate.getForObject(contains("London"), eq(CityWeather.class))).thenReturn(london);
        when(restTemplate.getForObject(contains("Paris"),  eq(CityWeather.class))).thenReturn(paris);

        List<WeatherLog> result = weatherAPIImpl.getActualWeather("testKey", "London", "Paris", null, mockRequest);

        assertEquals(2, result.size());

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<WeatherLog>> captor = ArgumentCaptor.forClass(List.class);
        verify(weatherLogReposity, times(1)).saveAll(captor.capture());
        assertEquals(2, captor.getValue().size());
    }

    @Test
    public void testGetActualWeatherWithMultipleWeatherEntriesPerCity() throws Exception {
        Weather w1 = new Weather();
        w1.setId(800); w1.setDescription("clear sky");
        Weather w2 = new Weather();
        w2.setId(801); w2.setDescription("few clouds");

        Main main = new Main();
        main.setTemp(285f);

        CityWeather city = new CityWeather();
        city.setName("Berlin");
        city.setMain(main);
        city.setWeather(Arrays.asList(w1, w2));

        when(restTemplate.getForObject(contains("Berlin"), eq(CityWeather.class))).thenReturn(city);

        List<WeatherLog> result = weatherAPIImpl.getActualWeather("testKey", "Berlin", null, null, mockRequest);

        assertEquals(2, result.size());
        assertEquals("clear sky", result.get(0).getActualWeather());
        assertEquals("few clouds", result.get(1).getActualWeather());
    }
}
