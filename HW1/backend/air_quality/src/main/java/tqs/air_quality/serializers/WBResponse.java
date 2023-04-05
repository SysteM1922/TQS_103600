package tqs.air_quality.serializers;

import java.util.List;

public abstract class WBResponse<T> {
	public String city_name;
	public String country_code;
	public Double lat;
	public Double lon;
	public List<T> data;
}
