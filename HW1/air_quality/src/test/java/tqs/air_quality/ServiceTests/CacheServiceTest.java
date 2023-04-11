package tqs.air_quality.ServiceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.air_quality.cache.Cache;
import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.serializers.WBCurrentResponse;
import tqs.air_quality.services.CacheService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheServiceTest {

	private CacheService cacheService = new CacheService();

	@BeforeEach
	public void setUp() {
		Cache.reset();
	}

	@Test
	public void testGetCacheStats() {
		assertEquals(0, cacheService.getCacheStats().getBody().hits);
		assertEquals(0, cacheService.getCacheStats().getBody().misses);
		assertEquals(0, cacheService.getCacheStats().getBody().requests);
		WBCurrentResponse current = new WBCurrentResponse();
		Cache.add("Lisbon", SearchType.CURRENT, DataSource.WEATHERBIT, current);
		Cache.add("Porto", SearchType.HISTORY, DataSource.WEATHERBIT, current);
		Cache.get("Coimbra", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.get("Lisbon", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.get("Porto", SearchType.HISTORY, DataSource.WEATHERBIT);
		Cache.get("Porto", SearchType.HISTORY, DataSource.OPENWEATHER);
		Cache.get("Porto", SearchType.CURRENT, DataSource.WEATHERBIT);
		assertEquals(2, cacheService.getCacheStats().getBody().hits);
		assertEquals(3, cacheService.getCacheStats().getBody().misses);
		assertEquals(5, cacheService.getCacheStats().getBody().requests);
	}
}
