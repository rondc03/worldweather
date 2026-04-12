package com.info.worldweather.controller;


import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.info.worldweather.impl.WeatherAPIImpl;
import com.info.worldweather.model.CityWeather;
import com.info.worldweather.model.WeatherLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "World.Weather", description = "Weather Information RESTful API")
@RequestMapping(value = "/v1/world/weather")
public class WeatherAPI {

    private static final Logger log = LoggerFactory.getLogger(WeatherAPI.class);

    @Autowired
    private WeatherAPIImpl weatherImpl;

    // GET: /v1/world/weather

   /* @Operation(summary = "Get Weather information of Selected City/ies", description = "This API is used for retrieving weather infomation by city/ies.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable"),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout")})
*/
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<CityWeather> getWeather(@RequestHeader(value = "appID", required = true) String appID,
                                 @Parameter(name = "cityName1", description = "City Name 1 (e.g. London", required = true) @RequestParam(value = "cityName1", required = true) String cityName1,
                                 @Parameter(name = "cityName2", description = "City Name 2 (e.g. Prague") @RequestParam(value = "cityName2", required = false) String cityName2,
                                 @Parameter(name = "cityName3", description = "City Name 3 (e.g. San Francisco") @RequestParam(value = "cityName3", required = false) String cityName3,
                                 HttpServletRequest request) throws Exception {

        log.info(request.getMethod() + " " + " INITIATED...");

        return weatherImpl.getWeather(appID, cityName1, cityName2, cityName3, request);
    }

    // POST: /v1/world/weather/actual

    /*@Operation(summary = "Post Actual Weather information of Selected City/ies", description = "This API is used for retrieving actual weather infomation by city/ies and save into databse.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable"),
            @ApiResponse(responseCode = "504", description = "Gateway Timeout")})
*/
    @RequestMapping(value = "/actual", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    List<WeatherLog> getActualWeather(@RequestHeader(value = "appID", required = true) String appID,
                                      @Parameter(name = "cityName1", description = "City Name 1 (e.g. London", required = true) @RequestParam(value = "cityName1", required = true) String cityName1,
                                      @Parameter(name = "cityName2", description = "City Name 2 (e.g. Prague") @RequestParam(value = "cityName2", required = false) String cityName2,
                                      @Parameter(name = "cityName3", description = "City Name 3 (e.g. San Francisco") @RequestParam(value = "cityName3", required = false) String cityName3,
                                      HttpServletRequest request) throws Exception {

        log.info(request.getMethod() + " " + " INITIATED...");

        return weatherImpl.getActualWeather(appID, cityName1, cityName2, cityName3, request);
    }


}