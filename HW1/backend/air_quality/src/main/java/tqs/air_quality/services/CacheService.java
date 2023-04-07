package tqs.air_quality.services;

import org.springframework.stereotype.Service;
import tqs.air_quality.cache.Cache;

@Service
public class CacheService {
	
	public Object getCacheStats() {
		return Cache.toJson();
	}
}
