package tqs.air_quality.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.WeatherBitData;

public class Cache {
	
	private static Map<String, CacheObject> cache = new HashMap<>();
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final static long expiration_time = 1200;
	private static Integer requests = 0;
	private static Integer hits = 0;
	private static Integer misses = 0;

	static {
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for (Map.Entry<String, CacheObject> entry : cache.entrySet()) {
					if (System.currentTimeMillis() - entry.getValue().getTimestamp() > expiration_time) {
						cache.remove(entry.getKey());
					}
				}
			}
		}, 0, 1, java.util.concurrent.TimeUnit.MINUTES);
	}

	public static void add(String searchName, SearchType type, DataSource source, WeatherBitData data) {
		cache.put(searchName, new CacheObject(data, type, source, 0));
	}
	
	public static void add(String searchName, SearchType type, DataSource source,  WeatherBitData data, long timestamp) {
		cache.put(searchName, new CacheObject(data, type, source, expiration_time-timestamp));
	}

	public static CacheObject get(String searchName, SearchType type, DataSource source) {
		requests++;
		if (cache.isEmpty() || cache.get(searchName) == null) {
			misses++;
			return null;
		}
		for (Map.Entry<String, CacheObject> entry : cache.entrySet()) {
			if (entry.getKey().equals(searchName) && entry.getValue().getSearchType().equals(type)
					&& (source == null || entry.getValue().getDataSource().equals(source))) {
				hits++;
				entry.getValue().updateTimestamp();
				return entry.getValue();
			}
		}
		misses++;
		return null;
	}

	public static String toJson () {
		return "{ \"requests\": " + requests + ", \"hits\": " + hits + ", \"misses\": " + misses + " }";
	}
}
