package models;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ClientSelection.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientSelection implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The report column id. */
	private Long reportColumnId;

	/** The category id. */
	private Long categoryId;

	/** The logical column id. */
	private Long logicalColumnId;

	/** The aggregation. */
	private String aggregation;

	/** The formula. */
	private String formula;

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The dimension. */
	private String dimension = "ROW";

	/** The properties. */
	private Map<String, Object> properties;

	/** The child properties. */
	// This properties are used to know the overridden properties
	private Map<String, Object> childProperties;

	/** The previous properties. */
	// This properties are used to reset property to inherited parent property
	private Map<String, Object> previousProperties;

	// Added by Raju to support of using report column in expression builder
	private String categoryName;

	/**
	 * Gets the category id.
	 *
	 * @return the category id
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id.
	 *
	 * @param categoryId the new category id
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Gets the logical column id.
	 *
	 * @return the logical column id
	 */
	public Long getLogicalColumnId() {
		return logicalColumnId;
	}

	/**
	 * Sets the logical column id.
	 *
	 * @param logicalColumnId the new logical column id
	 */
	public void setLogicalColumnId(Long logicalColumnId) {
		this.logicalColumnId = logicalColumnId;
	}

	/**
	 * Gets the aggregation.
	 *
	 * @return the aggregation
	 */
	public String getAggregation() {
		return aggregation;
	}

	/**
	 * Sets the aggregation.
	 *
	 * @param aggregation the new aggregation
	 */
	public void setAggregation(String aggregation) {
		this.aggregation = aggregation;
	}

	/**
	 * Gets the formula.
	 *
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}

	/**
	 * Sets the formula.
	 *
	 * @param formula the new formula
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the dimension.
	 *
	 * @return the dimension
	 */
	public String getDimension() {
		return dimension;
	}

	/**
	 * Sets the dimension.
	 *
	 * @param dimension the new dimension
	 */
	public void setDimension(String dimension) {
		this.dimension = dimension;
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
	 * Gets the report column id.
	 *
	 * @return the report column id
	 */
	public Long getReportColumnId() {
		return reportColumnId;
	}

	/**
	 * Sets the report column id.
	 *
	 * @param reportColumnId the new report column id
	 */
	public void setReportColumnId(Long reportColumnId) {
		this.reportColumnId = reportColumnId;
	}

	/**
	 * Gets the child properties.
	 *
	 * @return the child properties
	 */
	public Map<String, Object> getChildProperties() {
		return childProperties;
	}

	/**
	 * Sets the child properties.
	 *
	 * @param childProperties the child properties
	 */
	public void setChildProperties(Map<String, Object> childProperties) {
		this.childProperties = childProperties;
	}

	/**
	 * Gets the previous properties.
	 *
	 * @return the previous properties
	 */
	public Map<String, Object> getPreviousProperties() {
		return previousProperties;
	}

	/**
	 * Sets the previous properties.
	 *
	 * @param previousProperties the previous properties
	 */
	public void setPreviousProperties(Map<String, Object> previousProperties) {
		this.previousProperties = previousProperties;
	}



	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ClientSelection [reportColumnId=" + reportColumnId + ", categoryId=" + categoryId + ", logicalColumnId="
				+ logicalColumnId + ", aggregation=" + aggregation + ", formula=" + formula + ", name=" + name
				+ ", description=" + description + ", dimension=" + dimension + ", properties=" + properties + "]";
	}

}