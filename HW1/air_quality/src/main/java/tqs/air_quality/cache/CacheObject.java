package tqs.air_quality.cache;

import tqs.air_quality.models.DataSource;
import tqs.air_quality.models.SearchType;
import tqs.air_quality.models.serializers.WBResponse;

public class CacheObject {
	
	private WBResponse data;
	private SearchType searchType;
	private DataSource dataSource;
	private long timeStamp;
	static final long EXPIRATIONTIME = 600000;
	
	public CacheObject(WBResponse data, SearchType searchType, DataSource dataSource) {
		this.data = data;
		this.searchType = searchType;
		this.dataSource = dataSource;
		this.timeStamp = System.currentTimeMillis();
	}
	
	public CacheObject(WBResponse data, SearchType searchType, DataSource dataSource, long timeStamp) {
		this.data = data;
		this.searchType = searchType;
		this.dataSource = dataSource;
		this.timeStamp = System.currentTimeMillis() - EXPIRATIONTIME + timeStamp;
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
		return timeStamp;
	}

	public void updateTimestamp() {
		this.timeStamp = System.currentTimeMillis();
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - timeStamp > EXPIRATIONTIME;
	}
	
}
