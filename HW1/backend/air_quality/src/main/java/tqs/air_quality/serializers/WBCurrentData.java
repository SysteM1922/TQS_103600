package tqs.air_quality.serializers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import tqs.air_quality.models.BasicAirQualityResults;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WBCurrentData extends BasicAirQualityResults {
	
	@JsonProperty("aqi")
	public Integer aqi;
	@JsonProperty("co")
	public Double co;
	@JsonProperty("mold_level")
	public Integer mold_level;
	@JsonProperty("no2")
	public Double no2;
	@JsonProperty("o3")
	public Double o3;
	@JsonProperty("pm10")
	public Double pm10;
	@JsonProperty("pm25")
	public Double pm25;
	@JsonProperty("pollen_level_grass")
	public Integer pollen_level_grass;
	@JsonProperty("pollen_level_tree")
	public Integer pollen_level_tree;
	@JsonProperty("pollen_level_weed")
	public Integer pollen_level_weed;
	@JsonProperty("predominant_pollen_type")
	public String predominant_pollen_type;
	@JsonProperty("so2")
	public Double so2;

}
