package tqs.air_quality.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;

public class Cache {
	
	private static Map<String, CacheObject> cache = new HashMap<>();
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static Integer requests = 0;
	private static Integer hits = 0;
	private static Integer misses = 0;

	static {
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for (Map.Entry<String, CacheObject> entry : cache.entrySet()) {
					if (entry.getValue().isExpired()) {
						cache.remove(entry.getKey());
					}
				}
			}
		}, 0, 1, java.util.concurrent.TimeUnit.MINUTES);
	}

	public static void add(String searchName, SearchType type, DataSource source, Object data) {
		cache.put(searchName, new CacheObject(data, type, source));
	}
	
	public static void add(String searchName, SearchType type, DataSource source,  Object data, long timestamp) {
		cache.put(searchName, new CacheObject(data, type, source, timestamp));
	}

	public static Object get(String searchName, SearchType type, DataSource source) {
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
				return entry.getValue().getData();
			}
		}
		misses++;
		return null;
	}

	public static Object toJson() {
		return new Object() {
			public final Integer requests = Cache.requests;
			public final Integer hits = Cache.hits;
			public final Integer misses = Cache.misses;
		};
	}

	public static Map<String, CacheObject> getCache() {
		return cache;
	}

	public static Integer getRequests() {
		return requests;
	}

	public static Integer getHits() {
		return hits;
	}

	public static Integer getMisses() {
		return misses;
	}

	public static void reset() {
		cache.clear();
		requests = 0;
		hits = 0;
		misses = 0;
	}

}
