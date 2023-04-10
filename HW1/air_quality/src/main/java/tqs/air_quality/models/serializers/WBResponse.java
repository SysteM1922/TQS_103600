package tqs.air_quality.models.serializers;

import java.util.List;

public interface WBResponse {
	
	public String city_name = null;
	public String country_code = null;
	public Double lat = null;
	public Double lon = null;
	public List<WBData> data = null;
}
