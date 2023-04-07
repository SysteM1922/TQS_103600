package tqs.air_quality.serializers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import tqs.air_quality.models.BasicAirQualityResults;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WBForecastData extends BasicAirQualityResults {
	
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