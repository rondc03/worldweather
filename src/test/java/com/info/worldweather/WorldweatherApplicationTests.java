package com.info.worldweather;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.info.worldweather.repository.WeatherLogReposity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorldweatherApplicationTests {

	@MockBean
	private WeatherLogReposity weatherLogReposity;

	@MockBean
	private RestTemplate restTemplate;

	@Test
	public void contextLoads() {
	}
}
