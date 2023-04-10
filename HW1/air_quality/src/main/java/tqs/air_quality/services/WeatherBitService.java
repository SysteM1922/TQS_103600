package tqs.air_quality.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.serializers.*;
import tqs.air_quality.cache.*;

@Service
public class WeatherBitService {
	
	private final String CURRENT_URL = "https://api.weatherbit.io/v2.0/current/airquality";
	private final String HISTORICAL_URL = "https://api.weatherbit.io/v2.0/history/airquality";
	private final String FORECAST_URL = "https://api.weatherbit.io/v2.0/forecast/airquality";
	
	private final String API_KEY = "e5c32e0651744cdf96f8c8b0407edd4a";

	private RestTemplate restTemplate = new RestTemplate();
	private final static ObjectMapper mapper = new ObjectMapper();

	public ResponseEntity<WBResponse> getCurrent(String city) {
		WBResponse cacheObject = Cache.get(city, SearchType.CURRENT, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.CURRENT, city, null, null);
		if (response.getStatusCode().value() == 200) {
			WBResponse data = parseWBCurrent(response.getBody());
			Cache.add(city, SearchType.CURRENT, DataSource.WEATHERBIT, data);
			return ResponseEntity.status(response.getStatusCode()).body(data);
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<WBResponse> getHistory(String city) {
		WBResponse cacheObject = Cache.get(city, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, null, null);
		if (response.getStatusCode().value() == 200) {
			WBResponse data = parseWBHistory(response.getBody());
			Cache.add(city, SearchType.HISTORY, DataSource.WEATHERBIT, data);
			return ResponseEntity.status(response.getStatusCode()).body(data);
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<WBResponse> getHistory(String city, String start_date, String end_date) {
		WBResponse cacheObject = Cache.get(city+"/"+start_date+"/"+end_date, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, start_date, end_date);
		if (response.getStatusCode().value() == 200) {
			WBResponse data = parseWBHistory(response.getBody());
			Cache.add(city + "/" + start_date + "/" + end_date, SearchType.HISTORY, DataSource.WEATHERBIT, data, 120000);
			return ResponseEntity.status(response.getStatusCode()).body(data);
		}
		return ResponseEntity.status(response.getStatusCode()).body(null);
	}

	public ResponseEntity<WBResponse> getForecast(String city) {
		WBResponse cacheObject = Cache.get(city, SearchType.FORECAST, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.FORECAST, city, null, null);
		if (response.getStatusCode().value() == 200) {
			WBForecastResponse data = parseWBForecast(response.getBody());
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
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	public static WBCurrentResponse parseWBCurrent(String response) {
		try {
			return mapper.readValue(response, WBCurrentResponse.class);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static WBHistoryResponse parseWBHistory(String response) {
		try {
			return mapper.readValue(response, WBHistoryResponse.class);

		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static WBForecastResponse parseWBForecast(String response) {
		try {
			return mapper.readValue(response, WBForecastResponse.class);

		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
