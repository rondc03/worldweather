package com.info.worldweather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import org.springframework.security.access.prepost.PreAuthorize;

import com.info.worldweather.model.WeatherLog;

//@PreAuthorize("hasRole('ROLE_USER')")
@RepositoryRestResource(collectionResourceRel = "weatherLog", path = "weatherLog")
public interface WeatherLogReposity extends JpaRepository<WeatherLog, java.util.UUID> {

}
