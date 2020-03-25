package utils;

import java.util.List;

public class JsonModel {

	private String dbname;
	private String driver;
	private String url;
	private String username;
	private String password;
	private List<QueryModel> queries = null;

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<QueryModel> getQueries() {
		return queries;
	}

	public void setQueries(List<QueryModel> queries) {
		this.queries = queries;
	}
}
