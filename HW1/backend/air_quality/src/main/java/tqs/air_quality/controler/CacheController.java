package tqs.air_quality.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.air_quality.services.*;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

	@Autowired
	private CacheService cacheService;

	@GetMapping("/stats")
	public ResponseEntity<Object> getCache() {
		return ResponseEntity.status(200).body(cacheService.getCacheStats());
	}
}
