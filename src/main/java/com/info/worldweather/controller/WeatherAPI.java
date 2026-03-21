package com.info.worldweather.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.info.worldweather.impl.WeatherAPIImpl;
import com.info.worldweather.model.CityWeather;
import com.info.worldweather.model.WeatherLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableAutoConfiguration
@EnableSwagger2
// Define information of this Microservice for displaying on Swagger
@Api(tags = "World.Weather", value = "/v1/world/weather", description = "Weather Information RESTful API")
@RequestMapping(value = "/v1/world/weather")
public class WeatherAPI {

    private static final Logger log = LoggerFactory.getLogger(WeatherAPI.class);

    @Autowired
    private WeatherAPIImpl weatherImpl;

    // GET: /v1/world/weather

    // Define information of this API for displaying on Swagger
    @ApiOperation(value = "Get Weather information of Selected City/ies", nickname = "getWeather", notes = "This API is used for retrieving weather infomation by city/ies.")
    // Define information of HTTP response for this API for displaying on Swagger
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Gateway Timeout")})

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<CityWeather> getWeather(@RequestHeader(value = "appID", required = true) String appID,
                                 @ApiParam(name = "cityName1", value = "City Name 1 (e.g. London", required = true) @RequestParam(value = "cityName1", required = true) String cityName1,
                                 @ApiParam(name = "cityName2", value = "City Name 2 (e.g. Prague", required = false) @RequestParam(value = "cityName2", required = false) String cityName2,
                                 @ApiParam(name = "cityName3", value = "City Name 3 (e.g. San Francisco", required = false) @RequestParam(value = "cityName3", required = false) String cityName3,
                                 HttpServletRequest request) throws Exception {

        log.info(request.getMethod() + " " + " INITIATED...");

        return weatherImpl.getWeather(appID, cityName1, cityName2, cityName3, request);
    }

    // POST: /v1/world/weather/actual

    // Define information of this API for displaying on Swagger
    @ApiOperation(value = "Post Actual Weather information of Selected City/ies", nickname = "getActualWeather", notes = "This API is used for retrieving actual weather infomation by city/ies and save into databse.")
    // Define information of HTTP response for this API for displaying on Swagger
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 503, message = "Service Unavailable"),
            @ApiResponse(code = 504, message = "Gateway Timeout")})

    @RequestMapping(value = "/actual", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<WeatherLog> getActualWeather(@RequestHeader(value = "appID", required = true) String appID,
                                      @ApiParam(name = "cityName1", value = "City Name 1 (e.g. London", required = true) @RequestParam(value = "cityName1", required = true) String cityName1,
                                      @ApiParam(name = "cityName2", value = "City Name 2 (e.g. Prague", required = false) @RequestParam(value = "cityName2", required = false) String cityName2,
                                      @ApiParam(name = "cityName3", value = "City Name 3 (e.g. San Francisco", required = false) @RequestParam(value = "cityName3", required = false) String cityName3,
                                      HttpServletRequest request) throws Exception {

        log.info(request.getMethod() + " " + " INITIATED...");

        return weatherImpl.getActualWeather(appID, cityName1, cityName2, cityName3, request);
    }


}