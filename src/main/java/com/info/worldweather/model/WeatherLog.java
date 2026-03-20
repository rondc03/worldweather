package com.info.worldweather.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "weather_log")
@ApiModel(value = "WeatherLog", description = "Data model for Weather Information Result")
@JsonPropertyOrder({"id", "responseId", "location","actualWeather","temperature"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherLog {

	 @Id
	 @GeneratedValue(generator = "UUID")
	 @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	 @Column(updatable = false, nullable = false)
	 private UUID id;

	 @NotNull
	 @Size(min = 0, max = 50)
	 @Column(name = "response_id", length = 50)
	 private String responseId;

	 @NotNull
	 @Size(min = 0, max = 50)
	 @Column(name = "location", length = 50)
	 private String location;

	 @NotNull
	 @Size(min = 0, max = 255)
	 @Column(name = "actual_weather", length = 255)
	 private String actualWeather;

	 @NotNull
	 @Size(min = 0, max = 50)
	 @Column(name = "temperature", length = 50)
	 private String temperature;

	 //@NotNull
	 /*@Size(min = 0, max = 255)
	 @Column(name = "dtime_inserted", length = 255)
	 private String dtimeInserted;*/

	 // Getter Methods
	 @JsonProperty(value = "id", required = true)
	 @ApiModelProperty(position = 1, required = true, dataType = "string", example = "a3bb189e-8bf9-3888-9912-ace4e6543002", notes = "Unique UUID")
	 public UUID getId() {
	      return id;
	 }

	 @JsonProperty(value = "responseId", required = true)
	 @ApiModelProperty(position = 2, required = true, dataType = "String", example = "12345", notes = "Unique guId")
	 public String getResponseId() {
	      return responseId;
	 }

	 @JsonProperty(value = "location", required = true)
	 @ApiModelProperty(position = 3, required = true, dataType = "String", example = "London", notes = "Location")
	 public String getLocation() {
	      return location;
	 }

	 @JsonProperty(value = "actualWeather", required = true)
	 @ApiModelProperty(position = 4, required = true, dataType = "String", example = "overcast clouds", notes = "Description of Actual Weather")
	 public String getActualWeather() {
	      return actualWeather;
	 }

	 @JsonProperty(value = "temperature", required = true)
	 @ApiModelProperty(position = 5, required = true, dataType = "String", example = "275.88", notes = "Temperature")
	 public String getTemperature() {
	      return temperature;
	 }

	 /*@JsonProperty(value = "dtimeInserted", required = true)
	 @ApiModelProperty(position = 6, required = true, dataType = "String", example = "1548947970", notes = "Timestamp Record Inserted")
	 public String getDtimeInserted() {
	      return dtimeInserted;
	 }
*/

	 //Setter Methods
	 public void setId(UUID id) {
	      this.id = id;
	 }

	 public void setResponseId(String responseId) {
	      this.responseId = responseId;
	 }

	 public void setLocation(String location) {
	      this.location = location;
	 }

	 public void setActualWeather(String actualWeather) {
	      this.actualWeather = actualWeather;
	 }

	 public void setTemperature(String temperature) {
	      this.temperature = temperature;
	 }

	 /*public void setDtimeInserted(String dtimeInserted) {
	      this.dtimeInserted = dtimeInserted;
	 }*/
}
