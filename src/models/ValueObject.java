package models;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ValueObject.
 */
public class ValueObject implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data type. */
	private String dataType;

	/** The value type. */
	private int valueType;

	/** The value. */
	private Object value;

	/** The formula. */
	private String formula;

	/** The ui formula. */
	private String uiFormula;

	/** The client query model. */
	private ClientQueryModel clientQueryModel;

	/** The static values. */
	private List<Object> staticValues;

	/** The business object id. */
	private Long businessObjectId;
	
	private Boolean useReference = false;
	/**
	 * Gets the data type.
	 *
	 * @return the data type
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * Sets the data type.
	 *
	 * @param dataType the new data type
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * Gets the value type.
	 *
	 * @return the value type
	 */
	public int getValueType() {
		return valueType;
	}

	/**
	 * Sets the value type.
	 *
	 * @param valueType the new value type
	 */
	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value) {
		this.value = value;
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
	 * Gets the ui formula.
	 *
	 * @return the ui formula
	 */
	public String getUiFormula() {
		return uiFormula;
	}

	/**
	 * Sets the ui formula.
	 *
	 * @param uiFormula the new ui formula
	 */
	public void setUiFormula(String uiFormula) {
		this.uiFormula = uiFormula;
	}

	/**
	 * Gets the client query model.
	 *
	 * @return the client query model
	 */
	public ClientQueryModel getClientQueryModel() {
		return clientQueryModel;
	}

	/**
	 * Sets the client query model.
	 *
	 * @param clientQueryModel the new client query model
	 */
	public void setClientQueryModel(ClientQueryModel clientQueryModel) {
		this.clientQueryModel = clientQueryModel;
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

	public Boolean getUseReference() {
		return useReference;
	}

	public void setUseReference(Boolean useReference) {
		this.useReference = useReference;
	}
	
}
