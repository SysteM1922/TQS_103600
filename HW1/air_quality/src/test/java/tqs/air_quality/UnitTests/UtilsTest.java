package tqs.air_quality.UnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tqs.air_quality.services.WeatherBitService.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import tqs.air_quality.models.serializers.WBCurrentResponse;
import tqs.air_quality.models.serializers.WBForecastResponse;
import tqs.air_quality.models.serializers.WBHistoryResponse;

public class UtilsTest {
	
	@Test
	public void testParseFromWBCurrent() {
		String json = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":52,\"co\":200.2716,\"mold_level\":1,\"no2\":2,\"o3\":112,\"pm10\":7.1234508,\"pm25\":1.934503,\"pollen_level_grass\":2,\"pollen_level_tree\":2,\"pollen_level_weed\":2,\"predominant_pollen_type\":\"Trees\",\"so2\":0.1527369}],\"lat\":\"39.82219\",\"lon\":\"-7.49087\",\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
		WBCurrentResponse data = parseWBCurrent(json);
		assertEquals("Castelo Branco", data.city_name);
		assertEquals("PT", data.country_code);
		assertEquals(39.82219, data.lat);
		assertEquals(-7.49087, data.lon);
		assertThat(data.data).isNotNull();
	}

	@Test
	public void testParseFromWBForecast() {
		String json = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":37,\"co\":200.3,\"datetime\":\"2023-04-06:22\",\"no2\":1.2,\"o3\":80.1,\"pm10\":7.1,\"pm25\":1.9,\"so2\":0.2,\"timestamp_local\":\"2023-04-06T23:00:00\",\"timestamp_utc\":\"2023-04-06T22:00:00\",\"ts\":1680818400},{\"aqi\":30,\"co\":200.3,\"datetime\":\"2023-04-06:23\",\"no2\":0.8,\"o3\":64.1,\"pm10\":6.9,\"pm25\":1.8,\"so2\":0.1,\"timestamp_local\":\"2023-04-07T00:00:00\",\"timestamp_utc\":\"2023-04-06T23:00:00\",\"ts\":1680822000}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
		WBForecastResponse data = parseWBForecast(json);
		assertEquals("Castelo Branco", data.city_name);
		assertEquals("PT", data.country_code);
		assertEquals(39.82219, data.lat);
		assertEquals(-7.49087, data.lon);
		assertThat(data.data).isNotNull();
	}

	@Test
	public void testParseFromWBHistory() {
		String json = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":36,\"co\":80,\"datetime\":\"2023-04-06:21\",\"no2\":5,\"o3\":78,\"pm10\":7,\"pm25\":5,\"so2\":1,\"timestamp_local\":\"2023-04-06T22:00:00\",\"timestamp_utc\":\"2023-04-06T21:00:00\",\"ts\":1680814800},{\"aqi\":41,\"co\":78.3,\"datetime\":\"2023-04-06:20\",\"no2\":4,\"o3\":89.3,\"pm10\":6.3,\"pm25\":4.33,\"so2\":1,\"timestamp_local\":\"2023-04-06T21:00:00\",\"timestamp_utc\":\"2023-04-06T20:00:00\",\"ts\":1680811200}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}}";
		WBHistoryResponse data = parseWBHistory(json);
		assertEquals("Castelo Branco", data.city_name);
		assertEquals("PT", data.country_code);
		assertEquals(39.82219, data.lat);
		assertEquals(-7.49087, data.lon);
		assertThat(data.data).isNotNull();
	}
	
}
