package tqs.air_quality.UnitTests;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import tqs.air_quality.models.serializers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SerializersTest {

	final ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testWBCurrent() throws IOException {
		String json = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":52,\"co\":200.2716,\"mold_level\":1,\"no2\":2,\"o3\":112,\"pm10\":7.1234508,\"pm25\":1.934503,\"pollen_level_grass\":2,\"pollen_level_tree\":2,\"pollen_level_weed\":2,\"predominant_pollen_type\":\"Trees\",\"so2\":0.1527369}],\"lat\":\"39.82219\",\"lon\":\"-7.49087\",\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
		WBCurrentResponse response = mapper.readValue(json, WBCurrentResponse.class);
		assertEquals("Castelo Branco", response.city_name);
		assertEquals("PT", response.country_code);
		assertEquals(39.82219, response.lat);
		assertEquals(-7.49087, response.lon);
		assertEquals(1, response.data.size());
		assertEquals(52, response.data.get(0).aqi);
		assertEquals(200.2716, response.data.get(0).co);
		assertEquals(1, response.data.get(0).mold_level);
		assertEquals(2, response.data.get(0).no2);
		assertEquals(112, response.data.get(0).o3);
		assertEquals(7.1234508, response.data.get(0).pm10);
		assertEquals(1.934503, response.data.get(0).pm25);
		assertEquals(2, response.data.get(0).pollen_level_grass);
		assertEquals(2, response.data.get(0).pollen_level_tree);
		assertEquals(2, response.data.get(0).pollen_level_weed);
		assertEquals("Trees", response.data.get(0).predominant_pollen_type);
		assertEquals(0.1527369, response.data.get(0).so2);
	}

	@Test
	public void testWBForecast() throws IOException {
		String json = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":37,\"co\":200.3,\"datetime\":\"2023-04-06:22\",\"no2\":1.2,\"o3\":80.1,\"pm10\":7.1,\"pm25\":1.9,\"so2\":0.2,\"timestamp_local\":\"2023-04-06T23:00:00\",\"timestamp_utc\":\"2023-04-06T22:00:00\",\"ts\":1680818400},{\"aqi\":30,\"co\":200.3,\"datetime\":\"2023-04-06:23\",\"no2\":0.8,\"o3\":64.1,\"pm10\":6.9,\"pm25\":1.8,\"so2\":0.1,\"timestamp_local\":\"2023-04-07T00:00:00\",\"timestamp_utc\":\"2023-04-06T23:00:00\",\"ts\":1680822000}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
		WBForecastResponse response = mapper.readValue(json, WBForecastResponse.class);
		assertEquals("Castelo Branco", response.city_name);
		assertEquals("PT", response.country_code);
		assertEquals(39.82219, response.lat);
		assertEquals(-7.49087, response.lon);
		assertEquals(2, response.data.size());
		assertEquals(37, response.data.get(0).aqi);
		assertEquals(200.3, response.data.get(0).co);
		assertEquals(1.2, response.data.get(0).no2);
		assertEquals(80.1, response.data.get(0).o3);
		assertEquals(7.1, response.data.get(0).pm10);
		assertEquals(1.9, response.data.get(0).pm25);
		assertEquals(0.2, response.data.get(0).so2);
		assertEquals("2023-04-06T23:00:00", response.data.get(0).datetime);
		assertEquals(30, response.data.get(1).aqi);
		assertEquals(200.3, response.data.get(1).co);
		assertEquals(0.8, response.data.get(1).no2);
		assertEquals(64.1, response.data.get(1).o3);
		assertEquals(6.9, response.data.get(1).pm10);
		assertEquals(1.8, response.data.get(1).pm25);
		assertEquals(0.1, response.data.get(1).so2);
		assertEquals("2023-04-07T00:00:00", response.data.get(1).datetime);
	}

	@Test	
	public void testWBHistory() throws IOException {
		String json = "{\"city_name\":\"Castelo Branco\",\"country_code\":\"PT\",\"data\":[{\"aqi\":36,\"co\":80,\"datetime\":\"2023-04-06:21\",\"no2\":5,\"o3\":78,\"pm10\":7,\"pm25\":5,\"so2\":1,\"timestamp_local\":\"2023-04-06T22:00:00\",\"timestamp_utc\":\"2023-04-06T21:00:00\",\"ts\":1680814800},{\"aqi\":41,\"co\":78.3,\"datetime\":\"2023-04-06:20\",\"no2\":4,\"o3\":89.3,\"pm10\":6.3,\"pm25\":4.33,\"so2\":1,\"timestamp_local\":\"2023-04-06T21:00:00\",\"timestamp_utc\":\"2023-04-06T20:00:00\",\"ts\":1680811200}],\"lat\":39.82219,\"lon\":-7.49087,\"state_code\":\"06\",\"timezone\":\"Europe/Lisbon\"}";
		WBHistoryResponse response = mapper.readValue(json, WBHistoryResponse.class);
		assertEquals("Castelo Branco", response.city_name);
		assertEquals("PT", response.country_code);
		assertEquals(39.82219, response.lat);
		assertEquals(-7.49087, response.lon);
		assertEquals(2, response.data.size());
		assertEquals(36, response.data.get(0).aqi);
		assertEquals(80, response.data.get(0).co);
		assertEquals(5, response.data.get(0).no2);
		assertEquals(78, response.data.get(0).o3);
		assertEquals(7, response.data.get(0).pm10);
		assertEquals(5, response.data.get(0).pm25);
		assertEquals(1, response.data.get(0).so2);
		assertEquals("2023-04-06T22:00:00", response.data.get(0).datetime);
		assertEquals(41, response.data.get(1).aqi);
		assertEquals(78.3, response.data.get(1).co);
		assertEquals(4, response.data.get(1).no2);
		assertEquals(89.3, response.data.get(1).o3);
		assertEquals(6.3, response.data.get(1).pm10);
		assertEquals(4.33, response.data.get(1).pm25);
		assertEquals(1, response.data.get(1).so2);
		assertEquals("2023-04-06T21:00:00", response.data.get(1).datetime);
	}

	@Test
	public void testWBData() {
		assertThat(WBData.aqi).isNull();
		assertThat(WBData.co).isNull();
		assertThat(WBData.no2).isNull();
		assertThat(WBData.o3).isNull();
		assertThat(WBData.pm10).isNull();
		assertThat(WBData.pm25).isNull();
		assertThat(WBData.so2).isNull();
	}

	@Test
	public void testWBresponse() {
		assertThat(WBResponse.city_name).isNull();
		assertThat(WBResponse.country_code).isNull();
		assertThat(WBResponse.lat).isNull();
		assertThat(WBResponse.lon).isNull();
		assertThat(WBResponse.data).isNull();
	}

	@Test
	public void testWBCurrentData() {
		WBCurrentResponse.WBCurrentData data1 = new WBCurrentResponse.WBCurrentData();
		data1.aqi = 42;
		data1.co = 1.23;
		data1.mold_level = 1;
		data1.no2 = 0.5;
		data1.o3 = 0.1;
		data1.pm10 = 20.0;
		data1.pm25 = 10.0;
		data1.pollen_level_grass = 2;
		data1.pollen_level_tree = 0;
		data1.pollen_level_weed = 0;
		data1.predominant_pollen_type = "grass";
		data1.so2 = 0.2;

		WBCurrentResponse.WBCurrentData data2 = new WBCurrentResponse.WBCurrentData();
		data2.aqi = 42;
		data2.co = 1.23;
		data2.mold_level = 1;
		data2.no2 = 0.5;
		data2.o3 = 0.1;
		data2.pm10 = 20.0;
		data2.pm25 = 10.0;
		data2.pollen_level_grass = 2;
		data2.pollen_level_tree = 0;
		data2.pollen_level_weed = 0;
		data2.predominant_pollen_type = "grass";
		data2.so2 = 0.2;

		WBCurrentResponse.WBCurrentData data3 = new WBCurrentResponse.WBCurrentData();
		data3.aqi = 42;
		data3.co = 1.23;
		data3.mold_level = 1;
		data3.no2 = 0.5;
		data3.o3 = 0.1;
		data3.pm10 = 20.0;
		data3.pm25 = 10.0;
		data3.pollen_level_grass = 2;
		data3.pollen_level_tree = 0;
		data3.pollen_level_weed = 0;
		data3.predominant_pollen_type = "tree";
		data3.so2 = 0.2;

		assertEquals(data1, data2);
		assertEquals(data2, data1);

		assertNotEquals(data1, data3);
		assertNotEquals(data2, data3);

		assertNotEquals(null, data1);
	}

	@Test
	public void testWBHistoryData() {
		WBHistoryResponse.WBHistoryData data1 = new WBHistoryResponse.WBHistoryData();
		data1.aqi = 42;
		data1.co = 1.23;
		data1.no2 = 0.5;
		data1.o3 = 0.1;
		data1.pm10 = 20.0;
		data1.pm25 = 10.0;
		data1.so2 = 0.2;
		data1.datetime = "2023-04-06T22:00:00";

		WBHistoryResponse.WBHistoryData data2 = new WBHistoryResponse.WBHistoryData();
		data2.aqi = 42;
		data2.co = 1.23;
		data2.no2 = 0.5;
		data2.o3 = 0.1;
		data2.pm10 = 20.0;
		data2.pm25 = 10.0;
		data2.so2 = 0.2;
		data2.datetime = "2023-04-06T22:00:00";

		WBHistoryResponse.WBHistoryData data3 = new WBHistoryResponse.WBHistoryData();
		data3.aqi = 42;
		data3.co = 1.23;
		data3.no2 = 0.5;
		data3.o3 = 0.1;
		data3.pm10 = 20.0;
		data3.pm25 = 10.0;
		data3.so2 = 0.2;
		data3.datetime = "2023-04-06T21:00:00";

		assertEquals(data1, data2);
		assertEquals(data2, data1);

		assertNotEquals(data1, data3);
		assertNotEquals(data2, data3);

		assertNotEquals(null, data1);
	}

	@Test
	public void testWBForecastData() {
		WBForecastResponse.WBForecastData data1 = new WBForecastResponse.WBForecastData();
		data1.aqi = 42;
		data1.co = 1.23;
		data1.no2 = 0.5;
		data1.o3 = 0.1;
		data1.pm10 = 20.0;
		data1.pm25 = 10.0;
		data1.so2 = 0.2;
		data1.datetime = "2023-04-06T22:00:00";

		WBForecastResponse.WBForecastData data2 = new WBForecastResponse.WBForecastData();
		data2.aqi = 42;
		data2.co = 1.23;
		data2.no2 = 0.5;
		data2.o3 = 0.1;
		data2.pm10 = 20.0;
		data2.pm25 = 10.0;
		data2.so2 = 0.2;
		data2.datetime = "2023-04-06T22:00:00";

		WBForecastResponse.WBForecastData data3 = new WBForecastResponse.WBForecastData();
		data3.aqi = 42;
		data3.co = 1.23;
		data3.no2 = 0.5;
		data3.o3 = 0.1;
		data3.pm10 = 20.0;
		data3.pm25 = 10.0;
		data3.so2 = 0.2;
		data3.datetime = "2023-04-06T21:00:00";

		assertEquals(data1, data2);
		assertEquals(data2, data1);

		assertNotEquals(data1, data3);
		assertNotEquals(data2, data3);

		assertNotEquals(null, data1);
	}
}
