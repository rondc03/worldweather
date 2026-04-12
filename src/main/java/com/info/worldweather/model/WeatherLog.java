package com.info.worldweather.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.UuidGenerator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "weather_log")
@Schema(name = "WeatherLog", description = "Data model for Weather Information Result")
@JsonPropertyOrder({"id", "responseId", "location","actualWeather","temperature"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherLog {

	 @Id
	 @UuidGenerator
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
	 @Schema(description = "Unique UUID", example = "a3bb189e-8bf9-3888-9912-ace4e6543002", requiredMode = Schema.RequiredMode.REQUIRED)
	 public UUID getId() {
	      return id;
	 }

	 @JsonProperty(value = "responseId", required = true)
	 @Schema(description = "Unique guId", example = "12345", requiredMode = Schema.RequiredMode.REQUIRED)
	 public String getResponseId() {
	      return responseId;
	 }

	 @JsonProperty(value = "location", required = true)
	 @Schema(description = "Location", example = "London", requiredMode = Schema.RequiredMode.REQUIRED)
	 public String getLocation() {
	      return location;
	 }

	 @JsonProperty(value = "actualWeather", required = true)
	 @Schema(description = "Description of Actual Weather", example = "overcast clouds", requiredMode = Schema.RequiredMode.REQUIRED)
	 public String getActualWeather() {
	      return actualWeather;
	 }

	 @JsonProperty(value = "temperature", required = true)
	 @Schema(description = "Temperature", example = "275.88", requiredMode = Schema.RequiredMode.REQUIRED)
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
