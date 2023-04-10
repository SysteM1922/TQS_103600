package tqs.air_quality.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import tqs.air_quality.cache.CacheObject;
import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.serializers.WBForecastResponse;

public class CacheObjectTest {

	@Test
	public void testGetData() {
		WBForecastResponse data = new WBForecastResponse();
		CacheObject cacheObject = new CacheObject(data, null, null);
		assertEquals(data, cacheObject.getData());
	}

	@Test
	public void testGetSearchType() {
		CacheObject cacheObject = new CacheObject(null, SearchType.CURRENT, null);
		assertEquals(SearchType.CURRENT, cacheObject.getSearchType());
	}

	@Test
	public void testGetDataSource() {
		CacheObject cacheObject = new CacheObject(null, null, DataSource.WEATHERBIT);
		assertEquals(DataSource.WEATHERBIT, cacheObject.getDataSource());
	}

	@Test
	public void testGetTimestamp() {
		CacheObject cacheObject = new CacheObject(null, null, null, 1000);
		long timestamp = System.currentTimeMillis() - 600000 + 1000;
		assertEquals(true, cacheObject.getTimestamp() <= timestamp);
	}

	@Test
	public void testUpdateTimestamp() throws InterruptedException {
		CacheObject cacheObject = new CacheObject(null, null, null);
		TimeUnit.MILLISECONDS.sleep(10);
		long timestamp = cacheObject.getTimestamp();
		cacheObject.updateTimestamp();
		assertEquals(true, cacheObject.getTimestamp() > timestamp);
	}

	@Test
	public void testIsExpired() throws InterruptedException {
		CacheObject cacheObject = new CacheObject(null, null, null);
		assertEquals(false, cacheObject.isExpired());
		CacheObject cacheObject2 = new CacheObject(null, null, null, 0);
		TimeUnit.MILLISECONDS.sleep(10);
		assertEquals(true, cacheObject2.isExpired());
	}
}
