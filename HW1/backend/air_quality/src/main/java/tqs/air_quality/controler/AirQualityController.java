package tqs.air_quality.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.air_quality.cache.Cache;
import tqs.air_quality.cache.CacheObject;
import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.WeatherBitData;
import tqs.air_quality.services.WeatherBitService;

@RestController
@RequestMapping("/api/airquality")
public class AirQualityController {

	@Autowired
	private WeatherBitService weatherBitService;

	@GetMapping("/current/{city}")
	public ResponseEntity<Object> getCurrentAirQuality(@PathVariable String city) {
		CacheObject cacheObject = Cache.get(city, SearchType.CURRENT, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject.getData());
		}
		ResponseEntity<WeatherBitData> response = weatherBitService.getCurrent(city);
		if (response.getStatusCode().value() == 200) {
			Cache.add(city, SearchType.CURRENT, DataSource.WEATHERBIT, response.getBody());
		}
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/forecast/{city}")
	public ResponseEntity<Object> getForecastAirQuality(@PathVariable String city) {
		CacheObject cacheObject = Cache.get(city, SearchType.FORECAST, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject.getData());
		}
		ResponseEntity<WeatherBitData> response = weatherBitService.getForecast(city);
		if (response.getStatusCode().value() == 200) {
			Cache.add(city, SearchType.FORECAST, DataSource.WEATHERBIT, response.getBody());
		}
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/history/{city}")
	public ResponseEntity<Object> getHistoryAirQuality(@PathVariable String city) {
		CacheObject cacheObject = Cache.get(city, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject.getData());
		}
		ResponseEntity<WeatherBitData> response = weatherBitService.getHistorical(city);
		if (response.getStatusCode().value() == 200) {
			Cache.add(city, SearchType.HISTORY, DataSource.WEATHERBIT, response.getBody());
		}
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/history/{city}/{start_date}/{end_date}")
	public ResponseEntity<Object> getHistoryAirQuality(@PathVariable String city, @PathVariable String start_date,
			@PathVariable String end_date) {
		CacheObject cacheObject = Cache.get(city+"/"+start_date+"/"+end_date, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject.getData());
		}
		ResponseEntity<WeatherBitData> response = weatherBitService.getHistorical(city, start_date, end_date);
		if (response.getStatusCode().value() == 200) {
			Cache.add(city + "/" + start_date + "/" + end_date, SearchType.HISTORY, DataSource.WEATHERBIT,
					response.getBody(), 600);
		}
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/cache")
	public ResponseEntity<Object> getCache() {
		return ResponseEntity.status(200).body(Cache.toJson());
	}
}
