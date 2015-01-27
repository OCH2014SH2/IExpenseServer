package model;

public class QueryConfig {
	
	private String userId;
	private int status;
	
	public QueryConfig(String userId, int status) {
		this.userId = userId;
		this.status = status;
	}
	
	public QueryConfig(String userId) {
		this.userId = userId;
		this.status = -1;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
