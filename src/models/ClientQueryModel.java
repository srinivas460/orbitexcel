package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.orbit.reporting.domain.metaobjects.business.types.QuerySettings;
//import com.orbit.reporting.domain.metaobjects.report.publisher.PublisherBand;
//import com.orbit.reporting.domain.metaobjects.report.script.ScriptBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ClientQueryModel.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientQueryModel implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The domain id. */
	private Long domainId;

	/** The business object id. */
	private Long businessObjectId;

	/** The logical model id. */
	private Long logicalModelId;

	/** The report id. */
	private Long reportId;

	/** The report type. */
	// private Boolean pivot = false;
	private String reportType;

	/** The process id. */
	private Long processId;

	/** The report name. */
	private String reportName;

	/** The query settings. */
	private QuerySettings querySettings = new QuerySettings();

	/** The schedule query settings. */
	private QuerySettings scheduleQuerySettings = new QuerySettings();

	/** The start row. */
	private Integer startRow = 0;

	/** The parameters. */
	private List<ClientParameter> parameters = new ArrayList<ClientParameter>();

	/** The selections. */
	private List<ClientSelection> selections = new ArrayList<ClientSelection>();

	/** The constraints. */
	private List<ClientConstraint> constraints = new ArrayList<ClientConstraint>();

	/** The orders. */
	private List<ClientOrder> orders = new ArrayList<ClientOrder>();

	/** The parameter values. */
	private Map<String, Object> parameterValues = new HashMap<String, Object>();

	/** The extra params. */
	private Map<String, Object> extraParams = new HashMap<String, Object>();

	/** The query generator. */
	private String queryGenerator = "basic";

	/** The output type. */
	private String outputType = "B"; // default Browser

	/** The dashboard part. */
	private boolean dashboardPart = false; // default Report

	/** The scheduled run. */
	private boolean scheduledRun = false; // Default run from browser

	/** The publisher band. */
//	private PublisherBand publisherBand;
//
//	/** The script bean. */
//	private ScriptBean scriptBean;

	/** The mode. */
	private String mode = "JSON";

	/** The constraint group. */
	private ConstraintGroup constraintGroup;

	/** The properties. */
	// for export excel purpose only.
	private Map<String, Object> properties = new HashMap<String, Object>();

	/** The apply row limit. */
	private boolean applyRowLimit = true;

	private Long baseReportId;
	/**
	 * Gets the domain id.
	 *
	 * @return the domain id
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * Sets the domain id.
	 *
	 * @param domainId the new domain id
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	/**
	 * Gets the business object id.
	 *
	 * @return the business object id
	 */
	public Long getBusinessObjectId() {
		return businessObjectId;
	}

	/**
	 * Sets the business object id.
	 *
	 * @param businessObjectId the new business object id
	 */
	public void setBusinessObjectId(Long businessObjectId) {
		this.businessObjectId = businessObjectId;
	}

	/**
	 * Gets the logical model id.
	 *
	 * @return the logical model id
	 */
	public Long getLogicalModelId() {
		return logicalModelId;
	}

	/**
	 * Sets the logical model id.
	 *
	 * @param logicalModelId the new logical model id
	 */
	public void setLogicalModelId(Long logicalModelId) {
		this.logicalModelId = logicalModelId;
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public List<ClientParameter> getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the new parameters
	 */
	public void setParameters(List<ClientParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the selections.
	 *
	 * @return the selections
	 */
	public List<ClientSelection> getSelections() {
		return selections;
	}

	/**
	 * Sets the selections.
	 *
	 * @param selections the new selections
	 */
	public void setSelections(List<ClientSelection> selections) {
		this.selections = selections;
	}

	/**
	 * Gets the constraints.
	 *
	 * @return the constraints
	 */
	public List<ClientConstraint> getConstraints() {
		return constraints;
	}

	/**
	 * Sets the constraints.
	 *
	 * @param constraints the new constraints
	 */
	public void setConstraints(List<ClientConstraint> constraints) {
		this.constraints = constraints;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public List<ClientOrder> getOrders() {
		return orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param orders the new orders
	 */
	public void setOrders(List<ClientOrder> orders) {
		this.orders = orders;
	}

	/**
	 * Gets the parameter values.
	 *
	 * @return the parameter values
	 */
	public Map<String, Object> getParameterValues() {
		return parameterValues;
	}

	/**
	 * Sets the parameter values.
	 *
	 * @param parameterValues the parameter values
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * Gets the extra params.
	 *
	 * @return the extra params
	 */
	public Map<String, Object> getExtraParams() {
		return extraParams;
	}

	/**
	 * Sets the extra params.
	 *
	 * @param extraParams the extra params
	 */
	public void setExtraParams(Map<String, Object> extraParams) {
		this.extraParams = extraParams;
	}

	/**
	 * Gets the query settings.
	 *
	 * @return the query settings
	 */
	public QuerySettings getQuerySettings() {
		return querySettings;
	}

	/**
	 * Sets the query settings.
	 *
	 * @param querySettings the new query settings
	 */
	public void setQuerySettings(QuerySettings querySettings) {
		this.querySettings = querySettings;
	}

	/**
	 * Gets the start row.
	 *
	 * @return the start row
	 */
	public Integer getStartRow() {
		return startRow;
	}

	/**
	 * Sets the start row.
	 *
	 * @param startRow the new start row
	 */
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	/**
	 * Gets the query generator.
	 *
	 * @return the query generator
	 */
	public String getQueryGenerator() {
		return queryGenerator;
	}

	/**
	 * Sets the query generator.
	 *
	 * @param queryGenerator the new query generator
	 */
	public void setQueryGenerator(String queryGenerator) {
		this.queryGenerator = queryGenerator;
	}

	/**
	 * Gets the process id.
	 *
	 * @return the process id
	 */
	public Long getProcessId() {
		return processId;
	}

	/**
	 * Sets the process id.
	 *
	 * @param processId the new process id
	 */
	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	/**
	 * Gets the report id.
	 *
	 * @return the report id
	 */
	public Long getReportId() {
		return reportId;
	}

	/**
	 * Sets the report id.
	 *
	 * @param reportId the new report id
	 */
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	/**
	 * Gets the output type.
	 *
	 * @return the output type
	 */
	public String getOutputType() {
		return outputType;
	}

	/**
	 * Sets the output type.
	 *
	 * @param outputType the new output type
	 */
	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	/**
	 * Checks if is dashboard part.
	 *
	 * @return true, if is dashboard part
	 */
	public boolean isDashboardPart() {
		return dashboardPart;
	}

	/**
	 * Sets the dashboard part.
	 *
	 * @param dashboardPart the new dashboard part
	 */
	public void setDashboardPart(boolean dashboardPart) {
		this.dashboardPart = dashboardPart;
	}

	/**
	 * Gets the report type.
	 *
	 * @return the report type
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * Sets the report type.
	 *
	 * @param reportType the new report type
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * Checks if is scheduled run.
	 *
	 * @return true, if is scheduled run
	 */
	public boolean isScheduledRun() {
		return scheduledRun;
	}

	/**
	 * Sets the scheduled run.
	 *
	 * @param scheduledRun the new scheduled run
	 */
	public void setScheduledRun(boolean scheduledRun) {
		this.scheduledRun = scheduledRun;
	}

	/**
	 * Gets the publisher band.
	 *
	 * @return the publisher band
	 */
//	public PublisherBand getPublisherBand() {
//		return publisherBand;
//	}
//
//	/**
//	 * Sets the publisher band.
//	 *
//	 * @param publisherBand the new publisher band
//	 */
//	public void setPublisherBand(PublisherBand publisherBand) {
//		this.publisherBand = publisherBand;
//	}

	/**
	 * Gets the script bean.
	 *
	 * @return the script bean
	 */
//	public ScriptBean getScriptBean() {
//		return scriptBean;
//	}
//
//	/**
//	 * Sets the script bean.
//	 *
//	 * @param scriptBean the new script bean
//	 */
//	public void setScriptBean(ScriptBean scriptBean) {
//		this.scriptBean = scriptBean;
//	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Sets the mode.
	 *
	 * @param mode the new mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Gets the constraint group.
	 *
	 * @return the constraint group
	 */
	public ConstraintGroup getConstraintGroup() {
		return constraintGroup;
	}

	/**
	 * Sets the constraint group.
	 *
	 * @param constraintGroup the new constraint group
	 */
	public void setConstraintGroup(ConstraintGroup constraintGroup) {
		this.constraintGroup = constraintGroup;
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties the properties
	 */
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Gets the report name.
	 *
	 * @return the report name
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Sets the report name.
	 *
	 * @param reportName the new report name
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Checks if is apply row limit.
	 *
	 * @return true, if is apply row limit
	 */
	public boolean isApplyRowLimit() {
		return applyRowLimit;
	}

	/**
	 * Sets the apply row limit.
	 *
	 * @param applyRowLimit the new apply row limit
	 */
	public void setApplyRowLimit(boolean applyRowLimit) {
		this.applyRowLimit = applyRowLimit;
	}

	/**
	 * Gets the schedule query settings.
	 *
	 * @return the schedule query settings
	 */
	public QuerySettings getScheduleQuerySettings() {
		return scheduleQuerySettings;
	}

	/**
	 * Sets the schedule query settings.
	 *
	 * @param scheduleQuerySettings the new schedule query settings
	 */
	public void setScheduleQuerySettings(QuerySettings scheduleQuerySettings) {
		this.scheduleQuerySettings = scheduleQuerySettings;
	}

	/**
	 * Find parameter.
	 *
	 * @param parameterName the parameter name
	 * @return the string
	 */
	@JsonIgnore
	public String findParameter(String parameterName) {
		try {
			List<ClientParameter> parameters = this.getParameters();
			for (ClientParameter clientParameter : parameters) {
				if (clientParameter.getName().equals(parameterName)) {
					return clientParameter.getLabel();
				}
			}
		} catch (NullPointerException e) {
		}
		return parameterName;
	}

	public Long getBaseReportId() {
		return baseReportId;
	}

	public void setBaseReportId(Long baseReportId) {
		this.baseReportId = baseReportId;
	}
}
