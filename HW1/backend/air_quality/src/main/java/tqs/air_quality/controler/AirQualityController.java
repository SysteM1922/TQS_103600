package tqs.air_quality.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.air_quality.services.*;

@RestController
@RequestMapping("/api/airquality")
public class AirQualityController {

	@Autowired
	private WeatherBitService weatherBitService;

	@GetMapping("/current/{city}")
	public ResponseEntity<Object> getCurrentAirQuality(@PathVariable String city) {
		ResponseEntity<Object> response = weatherBitService.getCurrent(city);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/forecast/{city}")
	public ResponseEntity<Object> getForecastAirQuality(@PathVariable String city) {
		ResponseEntity<Object> response = weatherBitService.getForecast(city);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/history/{city}")
	public ResponseEntity<Object> getHistoryAirQuality(@PathVariable String city) {
		ResponseEntity<Object> response = weatherBitService.getHistorical(city);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

	@GetMapping("/history/{city}/{start_date}/{end_date}")
	public ResponseEntity<Object> getHistoryAirQuality(@PathVariable String city, @PathVariable String start_date,
			@PathVariable String end_date) {
		ResponseEntity<Object> response = weatherBitService.getHistorical(city, start_date, end_date);
		return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}

}
