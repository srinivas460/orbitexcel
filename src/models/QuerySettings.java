package models;

import java.io.Serializable;

/**
 * The Class QuerySettings.
 */
public class QuerySettings implements Serializable {

	/** The do query log. */
	private Boolean doQueryLog = false;

	/** The max rows. */
	private Integer maxRows = -1;

	/** The query timeout. */
	private Integer queryTimeout = -1;

	/** The auto query. */
	private Boolean autoQuery = true;

	/** The disable distinct. */
	private boolean disableDistinct = true;

	/**
	 * Gets the do query log.
	 *
	 * @return the do query log
	 */
	public Boolean getDoQueryLog() {
		return doQueryLog;
	}

	/**
	 * Sets the do query log.
	 *
	 * @param doQueryLog the new do query log
	 */
	public void setDoQueryLog(Boolean doQueryLog) {
		this.doQueryLog = doQueryLog;
	}

	/**
	 * Gets the max rows.
	 *
	 * @return the max rows
	 */
	public Integer getMaxRows() {
		return maxRows;
	}

	/**
	 * Sets the max rows.
	 *
	 * @param maxRows the new max rows
	 */
	public void setMaxRows(Integer maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * Gets the query timeout.
	 *
	 * @return the query timeout
	 */
	public Integer getQueryTimeout() {
		return queryTimeout;
	}

	/**
	 * Sets the query timeout.
	 *
	 * @param queryTimeout the new query timeout
	 */
	public void setQueryTimeout(Integer queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	/**
	 * Gets the auto query.
	 *
	 * @return the auto query
	 */
	public Boolean getAutoQuery() {
		return autoQuery;
	}

	/**
	 * Sets the auto query.
	 *
	 * @param autoQuery the new auto query
	 */
	public void setAutoQuery(Boolean autoQuery) {
		this.autoQuery = autoQuery;
	}

	/**
	 * Checks if is disable distinct.
	 *
	 * @return true, if is disable distinct
	 */
	public boolean isDisableDistinct() {
		return disableDistinct;
	}

	/**
	 * Sets the disable distinct.
	 *
	 * @param disableDistinct the new disable distinct
	 */
	public void setDisableDistinct(boolean disableDistinct) {
		this.disableDistinct = disableDistinct;
	}
}
