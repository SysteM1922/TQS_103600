package tqs.air_quality.RestControllerTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import tqs.air_quality.AirQualityApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;


@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = AirQualityApplication.class)
@AutoConfigureMockMvc
public class AirQualityControllerIT {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testGetCurrentAirQuality() throws Exception {
		mvc.perform(get("/api/airquality/current/Porto"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.city_name", is("Porto")))
				.andExpect(jsonPath("$.country_code", is("PT")))
				.andExpect(jsonPath("$.lat", notNullValue()))
				.andExpect(jsonPath("$.lon", notNullValue()))
				.andExpect(jsonPath("$.data", notNullValue()))
				.andExpect(jsonPath("$.data[0].aqi", notNullValue()))
				.andExpect(jsonPath("$.data[0].co", notNullValue()))
				.andExpect(jsonPath("$.data[0].no2", notNullValue()))
				.andExpect(jsonPath("$.data[0].o3", notNullValue()))
				.andExpect(jsonPath("$.data[0].so2", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm25", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm10", notNullValue()))
				.andExpect(jsonPath("$.data[0].mold_level", notNullValue()))
				.andExpect(jsonPath("$.data[0].pollen_level_grass", notNullValue()))
				.andExpect(jsonPath("$.data[0].pollen_level_tree", notNullValue()))
				.andExpect(jsonPath("$.data[0].pollen_level_weed", notNullValue()))
				.andExpect(jsonPath("$.data[0].predominant_pollen_type", notNullValue()));
	}
	
	@Test
	public void testGetForecastAirQuality() throws Exception {
		mvc.perform(get("/api/airquality/forecast/Porto"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.city_name", is("Porto")))
				.andExpect(jsonPath("$.country_code", is("PT")))
				.andExpect(jsonPath("$.lat", notNullValue()))
				.andExpect(jsonPath("$.lon", notNullValue()))
				.andExpect(jsonPath("$.data", notNullValue()))
				.andExpect(jsonPath("$.data", hasSize(greaterThan(1))))
				.andExpect(jsonPath("$.data[0].aqi", notNullValue()))
				.andExpect(jsonPath("$.data[0].co", notNullValue()))
				.andExpect(jsonPath("$.data[0].no2", notNullValue()))
				.andExpect(jsonPath("$.data[0].o3", notNullValue()))
				.andExpect(jsonPath("$.data[0].so2", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm25", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm10", notNullValue()))
				.andExpect(jsonPath("$.data[0].timestamp_local", notNullValue()));
	}

	@Test
	public void testGetHistoryAirQuality() throws Exception {
		mvc.perform(get("/api/airquality/history/Porto"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.city_name", is("Porto")))
				.andExpect(jsonPath("$.country_code", is("PT")))
				.andExpect(jsonPath("$.lat", notNullValue()))
				.andExpect(jsonPath("$.lon", notNullValue()))
				.andExpect(jsonPath("$.data", notNullValue()))
				.andExpect(jsonPath("$.data", hasSize(greaterThan(1))))
				.andExpect(jsonPath("$.data[0].aqi", notNullValue()))
				.andExpect(jsonPath("$.data[0].co", notNullValue()))
				.andExpect(jsonPath("$.data[0].no2", notNullValue()))
				.andExpect(jsonPath("$.data[0].o3", notNullValue()))
				.andExpect(jsonPath("$.data[0].so2", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm25", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm10", notNullValue()))
				.andExpect(jsonPath("$.data[0].timestamp_local", notNullValue()));
		mvc.perform(get("/api/airquality/history/Porto/2023-04-09/2023-04-10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.city_name", is("Porto")))
				.andExpect(jsonPath("$.country_code", is("PT")))
				.andExpect(jsonPath("$.lat", notNullValue()))
				.andExpect(jsonPath("$.lon", notNullValue()))
				.andExpect(jsonPath("$.data", notNullValue()))
				.andExpect(jsonPath("$.data", hasSize(greaterThan(1))))
				.andExpect(jsonPath("$.data[0].aqi", notNullValue()))
				.andExpect(jsonPath("$.data[0].co", notNullValue()))
				.andExpect(jsonPath("$.data[0].no2", notNullValue()))
				.andExpect(jsonPath("$.data[0].o3", notNullValue()))
				.andExpect(jsonPath("$.data[0].so2", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm25", notNullValue()))
				.andExpect(jsonPath("$.data[0].pm10", notNullValue()))
				.andExpect(jsonPath("$.data[0].timestamp_local", notNullValue()));
	}

	@Test
	public void testGetAirQualityInvalidCity() throws Exception {
		mvc.perform(get("/api/airquality/current/invalidCt"))
				.andExpect(status().isNoContent());
		mvc.perform(get("/api/airquality/forecast/invalidCt"))
				.andExpect(status().isNoContent());
		mvc.perform(get("/api/airquality/history/invalidCt"))
				.andExpect(status().isNoContent());
	}

	@Test
	public void testGetHistoryAirQualityInvalidDate() throws Exception {
		mvc.perform(get("/api/airquality/history/Porto/2023-04-09/2023-04-08"))
				.andExpect(status().isBadRequest());
		mvc.perform(get("/api/airquality/history/Porto/2023-04-09/2023-04-09"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testInvalidEndPoint() throws Exception {
		mvc.perform(get("/api/airquality/invalid"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testOtherMethods() throws Exception {
		mvc.perform(delete("/api/airquality/current/Porto"))
				.andExpect(status().isMethodNotAllowed());
		mvc.perform(put("/api/airquality/current/Porto", "Porto"))
				.andExpect(status().isMethodNotAllowed());
		mvc.perform(post("/api/airquality/current/Porto", "Porto"))
				.andExpect(status().isMethodNotAllowed());
	}
	
}
