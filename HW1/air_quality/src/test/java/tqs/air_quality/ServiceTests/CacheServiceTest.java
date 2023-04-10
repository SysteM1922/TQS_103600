package tqs.air_quality.ServiceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tqs.air_quality.cache.Cache;
import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.serializers.WBCurrentResponse;
import tqs.air_quality.services.CacheService;

public class CacheServiceTest {

	private CacheService cacheService = new CacheService();

	@BeforeEach
	public void setUp() {
		Cache.reset();
	}

	@Test
	public void testGetCacheStats() {
		assert (cacheService.getCacheStats().getBody().hits == 0);
		assert (cacheService.getCacheStats().getBody().misses == 0);
		assert (cacheService.getCacheStats().getBody().requests == 0);
		WBCurrentResponse current = new WBCurrentResponse();
		Cache.add("Lisbon", SearchType.CURRENT, DataSource.WEATHERBIT, current);
		Cache.add("Porto", SearchType.HISTORY, DataSource.WEATHERBIT, current);
		Cache.get("Coimbra", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.get("Lisbon", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.get("Porto", SearchType.HISTORY, DataSource.WEATHERBIT);
		Cache.get("Porto", SearchType.HISTORY, DataSource.OPENWEATHER);
		Cache.get("Porto", SearchType.CURRENT, DataSource.WEATHERBIT);
		assert (cacheService.getCacheStats().getBody().hits == 2);
		assert (cacheService.getCacheStats().getBody().misses == 3);
		assert (cacheService.getCacheStats().getBody().requests == 5);
	}
}
