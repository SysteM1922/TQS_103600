package tqs.air_quality.ServiceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import tqs.air_quality.models.serializers.*;
import tqs.air_quality.services.WeatherBitService;

@ExtendWith(MockitoExtension.class)
public class WeatherBitServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@InjectMocks
	private WeatherBitService weatherBitService;

	final String KEY = "&key=e5c32e0651744cdf96f8c8b0407edd4a";
	final String CURRENT_URL = "https://api.weatherbit.io/v2.0/current/airquality";
	final String HISTORY_URL = "https://api.weatherbit.io/v2.0/history/airquality";
	final String FORECAST_URL = "https://api.weatherbit.io/v2.0/forecast/airquality";
	final String CITY = "?city=Castelo Branco";
	final String INVALID_CITY = "?city=invalidCt";

	final String VALID_CURRENT_RESPONSE = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":18,\"co\":203.60947,\"mold_level\":1,\"no2\":0.048195943,\"o3\":39.339066,\"pm10\":7.458896,\"pm25\":1.3330822,\"pollen_level_grass\":1,\"pollen_level_tree\":1,\"pollen_level_weed\":1,\"predominant_pollen_type\":\"Molds\",\"so2\":0.14342368}],\"lat\":\"39.82219\",\"lon\":\"-7.49087\",\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
	final String VALID_HISTORY_RESPONSE = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":36,\"co\":80,\"datetime\":\"2023-04-06:21\",\"no2\":5,\"o3\":78,\"pm10\":7,\"pm25\":5,\"so2\":1,\"timestamp_local\":\"2023-04-06T22:00:00\",\"timestamp_utc\":\"2023-04-06T21:00:00\",\"ts\":1680814800},{\"aqi\":41,\"co\":78.3,\"datetime\":\"2023-04-06:20\",\"no2\":4,\"o3\":89.3,\"pm10\":6.3,\"pm25\":4.33,\"so2\":1,\"timestamp_local\":\"2023-04-06T21:00:00\",\"timestamp_utc\":\"2023-04-06T20:00:00\",\"ts\":1680811200}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
	final String VALID_HISTORY_RESPONSE_WITH_DATES = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":19,\"co\":74,\"datetime\":\"2023-03-20:00\",\"no2\":0,\"o3\":42,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-20T00:00:00\",\"timestamp_utc\":\"2023-03-20T00:00:00\",\"ts\":1679270400},{\"aqi\":23,\"co\":73.7,\"datetime\":\"2023-03-19:23\",\"no2\":0,\"o3\":49,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T23:00:00\",\"timestamp_utc\":\"2023-03-19T23:00:00\",\"ts\":1679266800},{\"aqi\":24,\"co\":73.3,\"datetime\":\"2023-03-19:22\",\"no2\":0,\"o3\":52,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T22:00:00\",\"timestamp_utc\":\"2023-03-19T22:00:00\",\"ts\":1679263200},{\"aqi\":24,\"co\":73,\"datetime\":\"2023-03-19:21\",\"no2\":2,\"o3\":51,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T21:00:00\",\"timestamp_utc\":\"2023-03-19T21:00:00\",\"ts\":1679259600},{\"aqi\":26,\"co\":71.3,\"datetime\":\"2023-03-19:20\",\"no2\":1,\"o3\":56,\"pm10\":3.3,\"pm25\":2.33,\"so2\":1,\"timestamp_local\":\"2023-03-19T20:00:00\",\"timestamp_utc\":\"2023-03-19T20:00:00\",\"ts\":1679256000},{\"aqi\":36,\"co\":69.7,\"datetime\":\"2023-03-19:19\",\"no2\":1,\"o3\":77,\"pm10\":2.7,\"pm25\":1.67,\"so2\":1,\"timestamp_local\":\"2023-03-19T19:00:00\",\"timestamp_utc\":\"2023-03-19T19:00:00\",\"ts\":1679252400},{\"aqi\":48,\"co\":68,\"datetime\":\"2023-03-19:18\",\"no2\":0,\"o3\":104,\"pm10\":2,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T18:00:00\",\"timestamp_utc\":\"2023-03-19T18:00:00\",\"ts\":1679248800},{\"aqi\":49,\"co\":68,\"datetime\":\"2023-03-19:17\",\"no2\":0,\"o3\":107,\"pm10\":2,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T17:00:00\",\"timestamp_utc\":\"2023-03-19T17:00:00\",\"ts\":1679245200},{\"aqi\":50,\"co\":68,\"datetime\":\"2023-03-19:16\",\"no2\":0,\"o3\":108,\"pm10\":2,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T16:00:00\",\"timestamp_utc\":\"2023-03-19T16:00:00\",\"ts\":1679241600},{\"aqi\":50,\"co\":68,\"datetime\":\"2023-03-19:15\",\"no2\":0,\"o3\":109,\"pm10\":2,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T15:00:00\",\"timestamp_utc\":\"2023-03-19T15:00:00\",\"ts\":1679238000},{\"aqi\":51,\"co\":68.3,\"datetime\":\"2023-03-19:14\",\"no2\":0,\"o3\":110,\"pm10\":1.7,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T14:00:00\",\"timestamp_utc\":\"2023-03-19T14:00:00\",\"ts\":1679234400},{\"aqi\":53,\"co\":68.7,\"datetime\":\"2023-03-19:13\",\"no2\":0,\"o3\":115,\"pm10\":1.3,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T13:00:00\",\"timestamp_utc\":\"2023-03-19T13:00:00\",\"ts\":1679230800},{\"aqi\":48,\"co\":69,\"datetime\":\"2023-03-19:12\",\"no2\":0,\"o3\":104,\"pm10\":1,\"pm25\":1,\"so2\":1,\"timestamp_local\":\"2023-03-19T12:00:00\",\"timestamp_utc\":\"2023-03-19T12:00:00\",\"ts\":1679227200},{\"aqi\":46,\"co\":70,\"datetime\":\"2023-03-19:11\",\"no2\":0,\"o3\":100,\"pm10\":1.7,\"pm25\":1.33,\"so2\":1,\"timestamp_local\":\"2023-03-19T11:00:00\",\"timestamp_utc\":\"2023-03-19T11:00:00\",\"ts\":1679223600},{\"aqi\":45,\"co\":71,\"datetime\":\"2023-03-19:10\",\"no2\":0,\"o3\":98,\"pm10\":2.3,\"pm25\":1.67,\"so2\":1,\"timestamp_local\":\"2023-03-19T10:00:00\",\"timestamp_utc\":\"2023-03-19T10:00:00\",\"ts\":1679220000},{\"aqi\":40,\"co\":72,\"datetime\":\"2023-03-19:09\",\"no2\":0,\"o3\":87,\"pm10\":3,\"pm25\":2,\"so2\":1,\"timestamp_local\":\"2023-03-19T09:00:00\",\"timestamp_utc\":\"2023-03-19T09:00:00\",\"ts\":1679216400},{\"aqi\":37,\"co\":72.3,\"datetime\":\"2023-03-19:08\",\"no2\":0,\"o3\":80,\"pm10\":3.3,\"pm25\":2.33,\"so2\":1,\"timestamp_local\":\"2023-03-19T08:00:00\",\"timestamp_utc\":\"2023-03-19T08:00:00\",\"ts\":1679212800},{\"aqi\":35,\"co\":72.7,\"datetime\":\"2023-03-19:07\",\"no2\":0,\"o3\":75,\"pm10\":3.7,\"pm25\":2.67,\"so2\":1,\"timestamp_local\":\"2023-03-19T07:00:00\",\"timestamp_utc\":\"2023-03-19T07:00:00\",\"ts\":1679209200},{\"aqi\":32,\"co\":73,\"datetime\":\"2023-03-19:06\",\"no2\":0,\"o3\":69,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T06:00:00\",\"timestamp_utc\":\"2023-03-19T06:00:00\",\"ts\":1679205600},{\"aqi\":20,\"co\":73.3,\"datetime\":\"2023-03-19:05\",\"no2\":0,\"o3\":44,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T05:00:00\",\"timestamp_utc\":\"2023-03-19T05:00:00\",\"ts\":1679202000},{\"aqi\":24,\"co\":73.7,\"datetime\":\"2023-03-19:04\",\"no2\":0,\"o3\":51.7,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T04:00:00\",\"timestamp_utc\":\"2023-03-19T04:00:00\",\"ts\":1679198400},{\"aqi\":20,\"co\":74,\"datetime\":\"2023-03-19:03\",\"no2\":0,\"o3\":43,\"pm10\":4,\"pm25\":3,\"so2\":1,\"timestamp_local\":\"2023-03-19T03:00:00\",\"timestamp_utc\":\"2023-03-19T03:00:00\",\"ts\":1679194800},{\"aqi\":20,\"co\":74,\"datetime\":\"2023-03-19:02\",\"no2\":0.7,\"o3\":44,\"pm10\":4,\"pm25\":3,\"so2\":1.3,\"timestamp_local\":\"2023-03-19T02:00:00\",\"timestamp_utc\":\"2023-03-19T02:00:00\",\"ts\":1679191200},{\"aqi\":25,\"co\":74,\"datetime\":\"2023-03-19:01\",\"no2\":0,\"o3\":54,\"pm10\":4,\"pm25\":3,\"so2\":1.7,\"timestamp_local\":\"2023-03-19T01:00:00\",\"timestamp_utc\":\"2023-03-19T01:00:00\",\"ts\":1679187600},{\"aqi\":22,\"co\":74,\"datetime\":\"2023-03-19:00\",\"no2\":2,\"o3\":48,\"pm10\":4,\"pm25\":3,\"so2\":2,\"timestamp_local\":\"2023-03-19T00:00:00\",\"timestamp_utc\":\"2023-03-19T00:00:00\",\"ts\":1679184000}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
	final String VALID_FORECAST_RESPONSE = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":37,\"co\":200.3,\"datetime\":\"2023-04-06:22\",\"no2\":1.2,\"o3\":80.1,\"pm10\":7.1,\"pm25\":1.9,\"so2\":0.2,\"timestamp_local\":\"2023-04-06T23:00:00\",\"timestamp_utc\":\"2023-04-06T22:00:00\",\"ts\":1680818400},{\"aqi\":30,\"co\":200.3,\"datetime\":\"2023-04-06:23\",\"no2\":0.8,\"o3\":64.1,\"pm10\":6.9,\"pm25\":1.8,\"so2\":0.1,\"timestamp_local\":\"2023-04-07T00:00:00\",\"timestamp_utc\":\"2023-04-06T23:00:00\",\"ts\":1680822000}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";;
	final String INVALID_HISTORY_RESPONSE_WITH_DATES = "{\"error\":\"end_date is before start_date.\"}";
	final String INVALID_RESPONSE = "";

	@Test
	void whenRequestingCurrentWeather_thenReturnCorrectCurrentWeather() {
		Mockito.when(restTemplate.getForEntity(CURRENT_URL + CITY + KEY, String.class))
				.thenReturn(ResponseEntity.status(200).body(VALID_CURRENT_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getCurrent("Castelo Branco");
		assert (response.getStatusCode().value() == 200);
		WBCurrentResponse correct_response = WeatherBitService.parseWBCurrent(VALID_CURRENT_RESPONSE);
		assert (response.getBody().equals(correct_response));
	}

	@Test
	void whenRequestingCurrentWeather_thenReturnInvalidCurrentWeather() {
		Mockito.when(restTemplate.getForEntity(CURRENT_URL + INVALID_CITY + KEY, String.class))
				.thenReturn(ResponseEntity.status(204).body(INVALID_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getCurrent("invalidCt");
		assert (response.getStatusCode().value() == 204);
		assert (response.getBody() == null);
	}

	@Test
	void whenRequestingHistoricalWeather_thenReturnCorrectHistoricalWeather() {
		Mockito.when(restTemplate.getForEntity(HISTORY_URL + CITY + KEY, String.class))
				.thenReturn(ResponseEntity.status(200).body(VALID_HISTORY_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getHistory("Castelo Branco");
		assert (response.getStatusCode().value() == 200);
		WBHistoryResponse correct_response = WeatherBitService.parseWBHistory(VALID_HISTORY_RESPONSE);
		assert (response.getBody().equals(correct_response));
	}
	
	@Test
	void whenRequestingHistoricalWeather_thenReturnInvalidHistoricalWeather() {
		Mockito.when(restTemplate.getForEntity(HISTORY_URL + INVALID_CITY + KEY, String.class))
				.thenReturn(ResponseEntity.status(204).body(INVALID_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getHistory("invalidCt");
		assert (response.getStatusCode().value() == 204);
		assert (response.getBody() == null);
	}

	@Test
	void whenRequestingHistoricalWeatherWithDates_thenReturnCorrectHistoricalWeather() {
		Mockito.when(restTemplate.getForEntity(HISTORY_URL + CITY + "&start_date=2023-03-19&end_date=2023-03-20" + KEY,
				String.class))
				.thenReturn(ResponseEntity.status(200).body(VALID_HISTORY_RESPONSE_WITH_DATES));
		ResponseEntity<WBResponse> response = weatherBitService.getHistory("Castelo Branco", "2023-03-19",
				"2023-03-20");
		assert (response.getStatusCode().value() == 200);
		WBHistoryResponse correct_response = WeatherBitService.parseWBHistory(VALID_HISTORY_RESPONSE_WITH_DATES);
		assert (response.getBody().equals(correct_response));
	}
	
	@Test
	void whenRequestingHistoricalWeatherWithDates_thenReturnInvalidHistoricalWeather() {
		Mockito.when(restTemplate.getForEntity(
				HISTORY_URL + INVALID_CITY + "&start_date=2023-03-19&end_date=2023-03-20" + KEY,
				String.class))
				.thenReturn(ResponseEntity.status(204).body(INVALID_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getHistory("invalidCt", "2023-03-19", "2023-03-20");
		assert (response.getStatusCode().value() == 204);
		assert (response.getBody() == null);
	}

	@Test
	void whenRequestingHistoricalWeatherWithIncorrectDates_thenReturnInvalidHistoricalWeather() {
		Mockito.when(restTemplate.getForEntity(
				HISTORY_URL + CITY + "&start_date=2023-03-20&end_date=2023-03-19" + KEY,
				String.class))
				.thenReturn(ResponseEntity.status(500).body(INVALID_HISTORY_RESPONSE_WITH_DATES));
		ResponseEntity<WBResponse> response = weatherBitService.getHistory("Castelo Branco", "2023-03-19",
				"2023-03-20");
		assert (response.getStatusCode().value() == 500);
		assert (response.getBody() == null);
	}

	@Test
	void whenRequestingForecastWeather_thenReturnCorrectForecastWeather() {
		Mockito.when(restTemplate.getForEntity(FORECAST_URL + CITY + KEY, String.class))
				.thenReturn(ResponseEntity.status(200).body(VALID_FORECAST_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getForecast("Castelo Branco");
		assert (response.getStatusCode().value() == 200);
		WBForecastResponse correct_response = WeatherBitService.parseWBForecast(VALID_FORECAST_RESPONSE);
		assert (response.getBody().equals(correct_response));
	}

	@Test
	void whenRequestingForecastWeather_thenReturnInvalidForecastWeather() {
		Mockito.when(restTemplate.getForEntity(FORECAST_URL + INVALID_CITY + KEY, String.class))
				.thenReturn(ResponseEntity.status(204).body(INVALID_RESPONSE));
		ResponseEntity<WBResponse> response = weatherBitService.getForecast("invalidCt");
		assert (response.getStatusCode().value() == 204);
		assert (response.getBody() == null);
	}
}
