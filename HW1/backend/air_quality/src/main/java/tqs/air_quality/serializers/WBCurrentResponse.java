package tqs.air_quality.serializers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WBCurrentResponse extends WBResponse<WBCurrentData> {

	@JsonProperty("city_name")
	public String city_name;
	@JsonProperty("country_code")
	public String country_code;
	@JsonProperty("lat")
	public Double lat;
	@JsonProperty("lon")
	public Double lon;
	@JsonProperty("data")
	public List<WBCurrentData> data;

}

