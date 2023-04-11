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

	private static final String CURRENTURL = "https://api.weatherbit.io/v2.0/current/airquality";
	private static final String HISTORICALURL = "https://api.weatherbit.io/v2.0/history/airquality";
	private static final String FORECASTURL = "https://api.weatherbit.io/v2.0/forecast/airquality";

	private static final String APIKEY = "e5c32e0651744cdf96f8c8b0407edd4a";

	private RestTemplate restTemplate = new RestTemplate();
	private static final ObjectMapper mapper = new ObjectMapper();
	
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

	public ResponseEntity<WBResponse> getHistory(String city, String startDate, String endDate) {
		WBResponse cacheObject = Cache.get(city + "/" + startDate + "/" + endDate, SearchType.HISTORY, null);
		if (cacheObject != null) {
			return ResponseEntity.status(200).body(cacheObject);
		}
		ResponseEntity<String> response = apiCall(SearchType.HISTORY, city, startDate, endDate);
		if (response.getStatusCode().value() == 200) {
			WBResponse data = parseWBHistory(response.getBody());
			Cache.add(city + "/" + startDate + "/" + endDate, SearchType.HISTORY, DataSource.WEATHERBIT, data,
					120000);
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

	public ResponseEntity<String> apiCall(SearchType searchType, String city, String startDate, String endDate) {
		String url = "?city=" + city;
		switch (searchType) {
			case CURRENT:
				url = CURRENTURL + url;
				break;
			case HISTORY:
				url = HISTORICALURL + url;
				if (startDate != null && endDate != null) {
					url += "&start_date=" + startDate + "&end_date=" + endDate;
				}
				break;
			case FORECAST:
				url = FORECASTURL + url;
				break;
		}
		url += "&key=" + APIKEY;
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
