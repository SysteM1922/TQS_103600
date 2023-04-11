package tqs.air_quality.RestControllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import tqs.air_quality.AirQualityApplication;
import tqs.air_quality.cache.Cache;
import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = AirQualityApplication.class)
@AutoConfigureMockMvc
public class CacheControllerIT {
	
	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void resetCache() {
		Cache.reset();
	}

	@Test
	public void whenGetCacheStats_thenReturnCacheStats() throws Exception {
		Cache.get("Coimbra", SearchType.CURRENT, DataSource.WEATHERBIT);
		mvc.perform(get("/api/cache/stats"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.requests", is(1)))
				.andExpect(jsonPath("$.hits", is(0)))
				.andExpect(jsonPath("$.misses", is(1)));
	}
}
