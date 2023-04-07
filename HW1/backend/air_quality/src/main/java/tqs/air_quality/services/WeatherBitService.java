package tqs.air_quality.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.WeatherBitData;
import tqs.air_quality.serializers.*;
import tqs.air_quality.cache.*;

@Service
public class WeatherBitService {
	
	private final String CURRENT_URL = "https://api.weatherbit.io/v2.0/current/airquality";
	private final String HISTORICAL_URL = "https://api.weatherbit.io/v2.0/history/airquality";
	private final String FORECAST_URL = "https://api.weatherbit.io/v2.0/forecast/airquality";
	
	private final String API_KEY = "e5c32e0651744cdf96f8c8b0407edd4a";

	private final RestTemplate restTemplate = new RestTemplate();
	private final static ObjectMapper mapper = new ObjectMapper();

	public ResponseEntity<Object> getCurrent(String city) {
		Object cacheObject = Cache.get(city, SearchType.CURRENT, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.CURRENT, city, null, null);
		if (response.getStatusCode().value() == 200) {
			WeatherBitData data = parseFromWBCurrent(response.getBody());
			Cache.add(city, SearchType.CURRENT, DataSource.WEATHERBIT, data);
			return ResponseEntity.status(response.getStatusCode()).body(data);
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<Object> getHistorical(String city) {
		Object cacheObject = Cache.get(city, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, null, null);
		if (response.getStatusCode().value() == 200) {
			WeatherBitData data = parseFromWBHistory(response.getBody());
			Cache.add(city, SearchType.HISTORY, DataSource.WEATHERBIT, data);
			return ResponseEntity.status(response.getStatusCode()).body(data);
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<Object> getHistorical(String city, String start_date, String end_date) {
		Object cacheObject = Cache.get(city+"/"+start_date+"/"+end_date, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, start_date, end_date);
		if (response.getStatusCode().value() == 200) {
			WeatherBitData data = parseFromWBHistory(response.getBody());
			Cache.add(city + "/" + start_date + "/" + end_date, SearchType.HISTORY, DataSource.WEATHERBIT, data, 120000);
			return ResponseEntity.status(response.getStatusCode()).body(data);
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<Object> getForecast(String city) {
		Object cacheObject = Cache.get(city, SearchType.FORECAST, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.FORECAST, city, null, null);
		if (response.getStatusCode().value() == 200) {
			WeatherBitData data = parseFromWBForecast(response.getBody());
			Cache.add(city, SearchType.FORECAST, DataSource.WEATHERBIT, data);
			return ResponseEntity.status(response.getStatusCode()).body(data);
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

	public static WeatherBitData parseFromWBCurrent(String response) {
		try {
			return new WeatherBitData(mapper.readValue(response, WBCurrentResponse.class));
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static WeatherBitData parseFromWBHistory(String response) {
		try {
			return new WeatherBitData(mapper.readValue(response, WBHistoryResponse.class));

		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static WeatherBitData parseFromWBForecast(String response) {
		try {
			return new WeatherBitData(mapper.readValue(response, WBForecastResponse.class));

		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
