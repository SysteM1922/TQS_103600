package tqs.air_quality.models;

import tqs.air_quality.serializers.*;

public class WeatherBitData {
	
	private String city_name;
	private String country_code;
	private Double lat;
	private Double lon;
	private SearchType type;
	private Object data;

	public WeatherBitData(WBCurrentResponse response) {
		this.city_name = response.city_name;
		this.country_code = response.country_code;
		this.lat = response.lat;
		this.lon = response.lon;
		this.type = SearchType.CURRENT;
		this.data = response.data;
	}

	public WeatherBitData(WBHistoryResponse response) {
		this.city_name = response.city_name;
		this.country_code = response.country_code;
		this.lat = response.lat;
		this.lon = response.lon;
		this.type = SearchType.HISTORY;
		this.data = response.data;
	}

	public WeatherBitData(WBForecastResponse response) {
		this.city_name = response.city_name;
		this.country_code = response.country_code;
		this.lat = response.lat;
		this.lon = response.lon;
		this.type = SearchType.FORECAST;
		this.data = response.data;
	}

	public String getCity_name() {
		return city_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}

	public SearchType getType() {
		return type;
	}

	public Object getData() {
		return data;
	}
	
}
