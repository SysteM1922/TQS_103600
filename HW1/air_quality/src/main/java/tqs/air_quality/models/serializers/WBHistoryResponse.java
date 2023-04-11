package tqs.air_quality.models.serializers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WBHistoryResponse implements WBResponse {

	@JsonProperty("city_name")
	public String city_name;
	@JsonProperty("country_code")
	public String country_code;
	@JsonProperty("lat")
	public Double lat;
	@JsonProperty("lon")
	public Double lon;
	@JsonProperty("data")
	public List<WBHistoryData> data;

	@EqualsAndHashCode(callSuper = false)
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class WBHistoryData implements WBData {

		@JsonProperty("aqi")
		public Integer aqi;
		@JsonProperty("co")
		public Double co;
		@JsonProperty("no2")
		public Double no2;
		@JsonProperty("o3")
		public Double o3;
		@JsonProperty("pm10")
		public Double pm10;
		@JsonProperty("pm25")
		public Double pm25;
		@JsonProperty("so2")
		public Double so2;
		@JsonProperty("timestamp_local")
		public String datetime;
	}

}
