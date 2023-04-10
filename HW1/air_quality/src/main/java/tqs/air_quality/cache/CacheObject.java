package tqs.air_quality.cache;

import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.serializers.WBResponse;

public class CacheObject {
	
	private WBResponse data;
	private SearchType searchType;
	private DataSource dataSource;
	private long timestamp;
	static final long expiration_time = 600000;
	
	public CacheObject(WBResponse data, SearchType searchType, DataSource dataSource) {
		this.data = data;
		this.searchType = searchType;
		this.dataSource = dataSource;
		this.timestamp = System.currentTimeMillis();
	}
	
	public CacheObject(WBResponse data, SearchType searchType, DataSource dataSource, long timestamp) {
		this.data = data;
		this.searchType = searchType;
		this.dataSource = dataSource;
		this.timestamp = System.currentTimeMillis() - expiration_time + timestamp;
	}
	
	public WBResponse getData() {
		return data;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	public long getTimestamp() {
		return timestamp;
	}

	public void updateTimestamp() {
		this.timestamp = System.currentTimeMillis();
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - timestamp > expiration_time;
	}
	
}
