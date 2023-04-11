package tqs.air_quality.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.air_quality.models.serializers.WBResponse;
import tqs.air_quality.services.*;

@RestController
@RequestMapping("/api/airquality")
public class AirQualityController {

	@Autowired
	private WeatherBitService weatherBitService;

	@GetMapping("/current/{city}")
	public ResponseEntity<Object> getCurrentAirQuality(@PathVariable String city) {
		ResponseEntity<WBResponse> response = weatherBitService.getCurrent(city);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/forecast/{city}")
	public ResponseEntity<Object> getForecastAirQuality(@PathVariable String city) {
		ResponseEntity<WBResponse> response = weatherBitService.getForecast(city);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/history/{city}")
	public ResponseEntity<WBResponse> getHistoryAirQuality(@PathVariable String city) {
		ResponseEntity<WBResponse> response = weatherBitService.getHistory(city, null, null);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/history/{city}/{startDate}/{endDate}")
	public ResponseEntity<WBResponse> getHistoryAirQuality(@PathVariable String city, @PathVariable String startDate,
			@PathVariable String endDate) {
		ResponseEntity<WBResponse> response = weatherBitService.getHistory(city, startDate, endDate);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

}
