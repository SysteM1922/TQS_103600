package tqs.air_quality.RestControllerTests;

import org.mockito.Mockito;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.air_quality.controler.AirQualityController;
import tqs.air_quality.models.serializers.WBCurrentResponse;
import tqs.air_quality.models.serializers.WBForecastResponse;
import tqs.air_quality.models.serializers.WBHistoryResponse;
import tqs.air_quality.models.serializers.WBResponse;
import tqs.air_quality.services.WeatherBitService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(AirQualityController.class)
public class AirQualityControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private WeatherBitService weatherBitService;

	private final ObjectMapper mapper = new ObjectMapper();

	final String CITY = "Castelo Branco";
	final String INVALID_CITY = "invalidCt";
	final WBResponse CURRENT_CITY_RESPONSE = WeatherBitService.parseWBCurrent(
			"{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"lat\":39.82219,\"lon\":-7.49087,\"data\":[{\"aqi\":18,\"co\":205.2784,\"mold_level\":1,\"no2\":0.039493896,\"o3\":38.266182,\"pm10\":14.630127,\"pm25\":2.8489497,\"pollen_level_grass\":1,\"pollen_level_tree\":1,\"pollen_level_weed\":1,\"predominant_pollen_type\":\"Molds\",\"so2\":0.173226}]}");
	final WBResponse FORECAST_CITY_RESPONSE = WeatherBitService.parseWBForecast(
			"{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"lat\":39.82219,\"lon\":-7.49087,\"data\":[{\"aqi\":17,\"co\":201.9,\"no2\":0.0,\"o3\":36.8,\"pm10\":12.9,\"pm25\":2.7,\"so2\":0.2,\"timestamp_local\":\"2023-04-10T04:00:00\"},{\"aqi\":17,\"co\":201.9,\"no2\":0.0,\"o3\":36.8,\"pm10\":13.2,\"pm25\":2.9,\"so2\":0.2,\"timestamp_local\":\"2023-04-10T05:00:00\"}]}");
	final WBResponse HISTORY_CITY_RESPONSE = WeatherBitService.parseWBHistory(
			"{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"lat\":39.82219,\"lon\":-7.49087,\"data\":[{\"aqi\":51,\"co\":99.0,\"no2\":5.0,\"o3\":40.7,\"pm10\":19.3,\"pm25\":12.33,\"so2\":5.0,\"timestamp_local\":\"2023-04-10T03:00:00\"},{\"aqi\":51,\"co\":98.0,\"no2\":5.0,\"o3\":45.3,\"pm10\":19.7,\"pm25\":12.67,\"so2\":5.0,\"timestamp_local\":\"2023-04-10T02:00:00\"}]}");
	final WBResponse HISTORY_WITH_DATES_CITY_RESPONSE = WeatherBitService.parseWBHistory("{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"lat\":39.82219,\"lon\":-7.49087,\"data\":[{\"aqi\":54,\"co\":95.3,\"no2\":4.3,\"o3\":60.0,\"pm10\":21.7,\"pm25\":13.67,\"so2\":5.3,\"timestamp_local\":\"2023-04-10 T00:00:00\"},{\"aqi\":55,\"co\":93.7,\"no2\":3.7,\"o3\":70.0,\"pm10\":23.3,\"pm25\":14.33,\"so2\":5.7,\"timestamp_local\":\"2023-04-09 T23:00:00\"}]}");
	final WBResponse INVALID_CITY_RESPONSE = null;
	final String DATE1 = "2023-04-09";
	final String DATE2 = "2023-04-10";
	
	@Test
	public void whenGetCurrentAirQuality_thenReturnData() throws Exception {
		Mockito.when(weatherBitService.getCurrent(CITY)).thenReturn(ResponseEntity.ok(CURRENT_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/current/" + CITY).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		WBCurrentResponse new_response = mapper.readValue(response, WBCurrentResponse.class);
		assert (new_response.equals(CURRENT_CITY_RESPONSE));
		verify(weatherBitService, times(1)).getCurrent(CITY);
	}
	
	@Test
	public void whenGetCurrentAirQuality_thenReturnInvalid() throws Exception {
		Mockito.when(weatherBitService.getCurrent(INVALID_CITY)).thenReturn(ResponseEntity.ok(INVALID_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/current/" + INVALID_CITY).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		assert (response.equals(""));
		verify(weatherBitService, times(1)).getCurrent(INVALID_CITY);
	}

	@Test
	public void whenGetForecast_thenReturnData() throws Exception {
		Mockito.when(weatherBitService.getForecast(CITY)).thenReturn(ResponseEntity.ok(FORECAST_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/forecast/" + CITY).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		WBForecastResponse new_response = mapper.readValue(response, WBForecastResponse.class);
		assert (new_response.equals(FORECAST_CITY_RESPONSE));
		verify(weatherBitService, times(1)).getForecast(CITY);
	}

	@Test
	public void whenGetForecast_thenReturnInvalid() throws Exception {
		Mockito.when(weatherBitService.getForecast(INVALID_CITY)).thenReturn(ResponseEntity.ok(INVALID_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/forecast/" + INVALID_CITY).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		assert (response.equals(""));
		verify(weatherBitService, times(1)).getForecast(INVALID_CITY);
	}

	@Test
	public void whenGetHistory_thenReturnData() throws Exception {
		Mockito.when(weatherBitService.getHistory(CITY)).thenReturn(ResponseEntity.ok(HISTORY_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/history/" + CITY).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		WBHistoryResponse new_response = mapper.readValue(response, WBHistoryResponse.class);
		assert (new_response.equals(HISTORY_CITY_RESPONSE));
		verify(weatherBitService, times(1)).getHistory(CITY);
	}

	@Test
	public void whenGetHistory_thenReturnInvalid() throws Exception {
		Mockito.when(weatherBitService.getHistory(INVALID_CITY)).thenReturn(ResponseEntity.ok(INVALID_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/history/" + INVALID_CITY).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		assert (response.equals(""));
		verify(weatherBitService, times(1)).getHistory(INVALID_CITY);
	}

	@Test
	public void whenGetHistoryWithDates_thenReturnData() throws Exception {
		Mockito.when(weatherBitService.getHistory(CITY, DATE1, DATE2))
				.thenReturn(ResponseEntity.ok(HISTORY_WITH_DATES_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/history/" + CITY + "/" + DATE1 + "/" + DATE2)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		WBHistoryResponse new_response = mapper.readValue(response, WBHistoryResponse.class);
		assert (new_response.equals(HISTORY_WITH_DATES_CITY_RESPONSE));
		verify(weatherBitService, times(1)).getHistory(CITY, DATE1, DATE2);
	}

	@Test
	public void whenGetHistoryWithDates_thenReturnInvalid() throws Exception {
		Mockito.when(weatherBitService.getHistory(INVALID_CITY, DATE1, DATE2))
				.thenReturn(ResponseEntity.ok(INVALID_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/history/" + INVALID_CITY + "/" + DATE1 + "/" + DATE2)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		assert (response.equals(""));
		verify(weatherBitService, times(1)).getHistory(INVALID_CITY, DATE1, DATE2);
	}

	@Test
	public void whenGetHistoryWithInvalidDates_thenReturnInvalid() throws Exception {
		Mockito.when(weatherBitService.getHistory(CITY, DATE2, DATE1))
				.thenReturn(ResponseEntity.ok(INVALID_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/history/" + CITY + "/" + DATE2 + "/" + DATE1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		assert (response.equals(""));
		verify(weatherBitService, times(1)).getHistory(CITY, DATE2, DATE1);
	}

	@Test
	public void whenGetHistoryWithEqualDates_thenReturnInvalid() throws Exception {
		Mockito.when(weatherBitService.getHistory(CITY, DATE1, DATE1))
				.thenReturn(ResponseEntity.ok(INVALID_CITY_RESPONSE));
		MvcResult result = mvc.perform(
				get("/api/airquality/history/" + CITY + "/" + DATE1 + "/" + DATE1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String response = result.getResponse().getContentAsString();
		assert (response.equals(""));
		verify(weatherBitService, times(1)).getHistory(CITY, DATE1, DATE1);
	}


}
