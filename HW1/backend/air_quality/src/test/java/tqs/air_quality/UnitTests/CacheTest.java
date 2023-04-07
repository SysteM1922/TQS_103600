package tqs.air_quality.UnitTests;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tqs.air_quality.cache.Cache;
import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheTest {

	@BeforeEach
	public void setUp() {
		Cache.reset();
		assertThat(Cache.getCache().size()).isEqualTo(0);
	}
	
	@Test
	public void testAdd() {

		assertThat(Cache.getCache().size()).isEqualTo(0);
		Cache.add("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT, "data");
		assertThat(Cache.getCache().size()).isEqualTo(1);
	}
	
	@Test
	public void testGet() {
		Cache.add("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT, "data");
		assertThat(Cache.get("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT)).isEqualTo("data");
		Cache.add("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT, "data2", 0);
		assertThat(Cache.get("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT)).isEqualTo("data2");
		assertThat(Cache.get("pesquisa", SearchType.FORECAST, DataSource.WEATHERBIT)).isEqualTo(null);
		assertThat(Cache.get("pesquisa", SearchType.CURRENT, DataSource.OPENWEATHER)).isEqualTo(null);
	}

	@Test
	public void testStats() {
		Cache.add("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT, "data");
		assertThat(Cache.getRequests()).isEqualTo(0);
		assertThat(Cache.getHits()).isEqualTo(0);
		assertThat(Cache.getMisses()).isEqualTo(0);
		Cache.get("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT);
		assertThat(Cache.getRequests()).isEqualTo(1);
		assertThat(Cache.getHits()).isEqualTo(1);
		assertThat(Cache.getMisses()).isEqualTo(0);
		Cache.get("pesquisa2", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.add("pesquisa2", SearchType.CURRENT, DataSource.WEATHERBIT, "data2", 0);
		assertThat(Cache.getRequests()).isEqualTo(2);
		assertThat(Cache.getHits()).isEqualTo(1);
		assertThat(Cache.getMisses()).isEqualTo(1);
		Cache.get("pesquisa2", SearchType.CURRENT, DataSource.WEATHERBIT);
		assertThat(Cache.getRequests()).isEqualTo(3);
		assertThat(Cache.getHits()).isEqualTo(2);
		assertThat(Cache.getMisses()).isEqualTo(1);
	}

	@Test
	public void testReset() {
		Cache.add("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT, "data");
		Cache.get("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.get("pesquisa2", SearchType.CURRENT, DataSource.WEATHERBIT);
		Cache.add("pesquisa2", SearchType.CURRENT, DataSource.WEATHERBIT, "data2", 0);
		Cache.get("pesquisa2", SearchType.CURRENT, DataSource.WEATHERBIT);
		assertThat(Cache.getRequests()).isEqualTo(3);
		assertThat(Cache.getHits()).isEqualTo(2);
		assertThat(Cache.getMisses()).isEqualTo(1);
		Cache.reset();
		assertThat(Cache.getRequests()).isEqualTo(0);
		assertThat(Cache.getHits()).isEqualTo(0);
		assertThat(Cache.getMisses()).isEqualTo(0);
	}

	@Disabled
	@Test void schedulerTest() throws InterruptedException {
		Cache.add("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT, "data", 1);
		TimeUnit.SECONDS.sleep(60);
		assertThat(Cache.get("pesquisa", SearchType.CURRENT, DataSource.WEATHERBIT)).isNull();
	}
}
