package models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ClientParameter.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientParameter implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private String name;

	/** The label. */
	private String label;

	/** The type. */
	private DataType type;

	/** The default value. */
	private ValueObject defaultValue;

	/** The required. */
	private Boolean required = false;

	/** The no filter. */
	private Boolean noFilter = false;
	private Boolean includeTime = false;
	private List<ValueObject> defaultValues;

	/** The component type. */
	private String componentType;

	/** The query model. */
	private ClientQueryModel queryModel;

	/** The static values. */
	private List<Object> staticValues;

	/** The evaluated value. */
	private Object evaluatedValue;

	/** The evaluated values. */
	private List<Object> evaluatedValues;

	/**
	 * Instantiates a new client parameter.
	 */
	public ClientParameter() {

	}

	/**
	 * Instantiates a new client parameter.
	 *
	 * @param name         the name
	 * @param type         the type
	 * @param defaultValue the default value
	 */
	public ClientParameter(String name, DataType type, ValueObject defaultValue) {
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
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
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public DataType getType() {
		return type;
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
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(DataType type) {
		this.type = type;
	}

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(ValueObject defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public ValueObject getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Gets the required.
	 *
	 * @return the required
	 */
	public Boolean getRequired() {
		return required;
	}

	/**
	 * Sets the required.
	 *
	 * @param required the new required
	 */
	public void setRequired(Boolean required) {
		this.required = required;
	}

	/**
	 * Gets the no filter.
	 *
	 * @return the no filter
	 */
	public Boolean getNoFilter() {
		return noFilter;
	}

	/**
	 * Sets the no filter.
	 *
	 * @param noFilter the new no filter
	 */
	public void setNoFilter(Boolean noFilter) {
		this.noFilter = noFilter;
	}

	/**
	 * Gets the default values.
	 *
	 * @return the default values
	 */
	public List<ValueObject> getDefaultValues() {
		return defaultValues;
	}

	/**
	 * Sets the default values.
	 *
	 * @param defaultValues the new default values
	 */
	public void setDefaultValues(List<ValueObject> defaultValues) {
		this.defaultValues = defaultValues;
	}

	/**
	 * Gets the component type.
	 *
	 * @return the component type
	 */
	public String getComponentType() {
		return componentType;
	}

	/**
	 * Sets the component type.
	 *
	 * @param componentType the new component type
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	/**
	 * Gets the query model.
	 *
	 * @return the query model
	 */
	public ClientQueryModel getQueryModel() {
		return queryModel;
	}

	/**
	 * Sets the query model.
	 *
	 * @param queryModel the new query model
	 */
	public void setQueryModel(ClientQueryModel queryModel) {
		this.queryModel = queryModel;
	}

	/**
	 * Gets the static values.
	 *
	 * @return the static values
	 */
	public List<Object> getStaticValues() {
		return staticValues;
	}

	/**
	 * Sets the static values.
	 *
	 * @param staticValues the new static values
	 */
	public void setStaticValues(List<Object> staticValues) {
		this.staticValues = staticValues;
	}

	/**
	 * Gets the evaluated value.
	 *
	 * @return the evaluated value
	 */
	public Object getEvaluatedValue() {
		return evaluatedValue;
	}

	/**
	 * Sets the evaluated value.
	 *
	 * @param evaluatedValue the new evaluated value
	 */
	public void setEvaluatedValue(Object evaluatedValue) {
		this.evaluatedValue = evaluatedValue;
	}

	/**
	 * Gets the evaluated values.
	 *
	 * @return the evaluated values
	 */
	public List<Object> getEvaluatedValues() {
		return evaluatedValues;
	}

	/**
	 * Sets the evaluated values.
	 *
	 * @param evaluatedValues the new evaluated values
	 */
	public void setEvaluatedValues(List<Object> evaluatedValues) {
		this.evaluatedValues = evaluatedValues;
	}

	public Boolean getIncludeTime() {
		return includeTime;
	}

	public void setIncludeTime(Boolean includeTime) {
		this.includeTime = includeTime;
	}
	
}