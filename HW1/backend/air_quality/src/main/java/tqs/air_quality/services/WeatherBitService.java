package tqs.air_quality.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.WeatherBitData;
import tqs.air_quality.serializers.*;

@Service
public class WeatherBitService {
	
	private final String CURRENT_URL = "https://api.weatherbit.io/v2.0/current/airquality";
	private final String HISTORICAL_URL = "https://api.weatherbit.io/v2.0/history/airquality";
	private final String FORECAST_URL = "https://api.weatherbit.io/v2.0/forecast/airquality";
	
	private final String API_KEY = "e5c32e0651744cdf96f8c8b0407edd4a";

	private final RestTemplate restTemplate = new RestTemplate();
	private final static ObjectMapper mapper = new ObjectMapper();

	public ResponseEntity<WeatherBitData> getCurrent(String city) {
		ResponseEntity<String> response = apiCall(SearchType.CURRENT, city, null, null);
		if (response.getStatusCode().value() == 200) {
			return ResponseEntity.status(response.getStatusCode()).body(parseFromWBCurrent(response.getBody()));
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<WeatherBitData> getHistorical(String city) {
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, null, null);
		if (response.getStatusCode().value() == 200) {
			return ResponseEntity.status(response.getStatusCode()).body(parseFromWBHistory(response.getBody()));
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<WeatherBitData> getHistorical(String city, String start_date, String end_date) {
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, start_date, end_date);
		if (response.getStatusCode().value() == 200) {
			return ResponseEntity.status(response.getStatusCode()).body(parseFromWBHistory(response.getBody()));
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<WeatherBitData> getForecast(String city) {
		ResponseEntity<String> response = apiCall(SearchType.FORECAST, city, null, null);
		if (response.getStatusCode().value() == 200) {
			return ResponseEntity.status(response.getStatusCode()).body(parseFromWBForecast(response.getBody()));
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<String> apiCall(SearchType searchType, String city, String start_date, String end_date) {
		String url = "?city=" + city;
		switch (searchType) {
			case CURRENT:
				url = CURRENT_URL + url;
				break;
			case HISTORY:
				url = HISTORICAL_URL + url;
				break;
			case FORECAST:
				url = FORECAST_URL + url;
				break;
			default:
				break;
		}
		if (start_date != null && end_date != null) {
			url += "&start_date=" + start_date + "&end_date=" + end_date;
		}
		url += "&key=" + API_KEY;
		try {
			return restTemplate.getForEntity(url, String.class);
		} catch (HttpClientErrorException e) {
			return ResponseEntity.status(e.getStatusCode()).body(null);
		}
	}

	private static WeatherBitData parseFromWBCurrent(String response) {
		try {
			return new WeatherBitData(mapper.readValue(response, WBCurrentResponse.class));
		} catch (JsonProcessingException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private static WeatherBitData parseFromWBHistory(String response) {
		try {
			return new WeatherBitData(mapper.readValue(response, WBHistoryResponse.class));

		} catch (JsonProcessingException e) {
			return null;
		}
	}

	private static WeatherBitData parseFromWBForecast(String response) {
		try {
			return new WeatherBitData(mapper.readValue(response, WBForecastResponse.class));

		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
