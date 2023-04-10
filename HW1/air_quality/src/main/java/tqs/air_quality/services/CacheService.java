package tqs.air_quality.services;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tqs.air_quality.cache.Cache;
import tqs.air_quality.cache.Cache.CacheJson;

@Service
public class CacheService {
	
	public ResponseEntity<CacheJson> getCacheStats() {
		try {
		CacheJson cache = Cache.toJson();
			return ResponseEntity.ok(cache);
		} catch (Exception e) {
			return ResponseEntity.status(Response.SC_INTERNAL_SERVER_ERROR).build();
		}
	}
}
