package com.info.worldweather.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class WeatherLogTest {

    @Test
    public void testSetAndGetResponseId() {
        WeatherLog log = new WeatherLog();
        log.setResponseId("12345");
        assertEquals("12345", log.getResponseId());
    }

    @Test
    public void testSetAndGetLocation() {
        WeatherLog log = new WeatherLog();
        log.setLocation("London");
        assertEquals("London", log.getLocation());
    }

    @Test
    public void testSetAndGetActualWeather() {
        WeatherLog log = new WeatherLog();
        log.setActualWeather("overcast clouds");
        assertEquals("overcast clouds", log.getActualWeather());
    }

    @Test
    public void testSetAndGetTemperature() {
        WeatherLog log = new WeatherLog();
        log.setTemperature("275.88");
        assertEquals("275.88", log.getTemperature());
    }

    @Test
    public void testSetAndGetId() {
        WeatherLog log = new WeatherLog();
        UUID id = UUID.randomUUID();
        log.setId(id);
        assertEquals(id, log.getId());
    }

    @Test
    public void testDefaultIdIsNull() {
        WeatherLog log = new WeatherLog();
        assertNull(log.getId());
    }

    @Test
    public void testAllFieldsIndependent() {
        WeatherLog log = new WeatherLog();
        log.setResponseId("999");
        log.setLocation("Tokyo");
        log.setActualWeather("clear sky");
        log.setTemperature("300.0");

        assertEquals("999", log.getResponseId());
        assertEquals("Tokyo", log.getLocation());
        assertEquals("clear sky", log.getActualWeather());
        assertEquals("300.0", log.getTemperature());
    }
}
