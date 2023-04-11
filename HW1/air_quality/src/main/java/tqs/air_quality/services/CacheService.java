package tqs.air_quality.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tqs.air_quality.cache.Cache;
import tqs.air_quality.cache.Cache.CacheJson;

@Service
public class CacheService {
	
	public ResponseEntity<CacheJson> getCacheStats() {
		return ResponseEntity.ok(Cache.toJson());
	}
}
