package tqs.air_quality.RestControllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.air_quality.cache.Cache.CacheJson;
import tqs.air_quality.controler.CacheController;
import tqs.air_quality.services.CacheService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(CacheController.class)
public class CacheControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CacheService cacheService;

	@Test
	public void whenGetCacheStats_thenReturnCacheStats() throws Exception {
		CacheJson cache = new CacheJson(2, 1, 1);
		Mockito.when(cacheService.getCacheStats()).thenReturn(ResponseEntity.ok(cache));

		MvcResult result = mvc.perform(
				get("/api/cache/stats").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		CacheJson new_cache = mapper.readValue(response, CacheJson.class);
		assertEquals(new_cache, cache);
		verify(cacheService, times(1)).getCacheStats();
	}
}