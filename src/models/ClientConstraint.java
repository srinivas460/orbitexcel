package models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class ClientConstraint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientConstraint implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The combination type. */
	private String combinationType = "AND";

	/** The client selection. */
	private ClientSelection clientSelection;

	/** The operator. */
	private String operator;

	/** The expression. */
	private String expression;

	/** The value. */
	private Object value;

	/** The value type. */
	private int valueType = 0;

	/** The values. */
	private List<Object> values;

	/** The display. */
	private List<Object> display;

	/** The ignore case. */
	private Boolean ignoreCase = true;

	/** The parameter name. */
	private String parameterName;

	/** The constraint key. */
	private String constraintKey;

	/** The value object. */
	private ValueObject valueObject;

	/** The value objects. */
	private List<ValueObject> valueObjects;

	/**
	 * Instantiates a new client constraint.
	 */
	public ClientConstraint() {

	}

	/**
	 * Instantiates a new client constraint.
	 *
	 * @param combinationType the combination type
	 */
	public ClientConstraint(String combinationType) {
		this.combinationType = combinationType;
	}

	/**
	 * Gets the combination type.
	 *
	 * @return the combination type
	 */
	public String getCombinationType() {
		return combinationType;
	}

	/**
	 * Gets the operator.
	 *
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Sets the operator.
	 *
	 * @param operator the new operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * Gets the expression.
	 *
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Sets the expression.
	 *
	 * @param expression the new expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * Sets the combination type.
	 *
	 * @param combinationType the new combination type
	 */
	public void setCombinationType(String combinationType) {
		this.combinationType = combinationType;
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<Object> getValues() {
		return values;
	}

	/**
	 * Sets the values.
	 *
	 * @param values the new values
	 */
	public void setValues(List<Object> values) {
		this.values = values;
	}

	/**
	 * Gets the client selection.
	 *
	 * @return the client selection
	 */
	public ClientSelection getClientSelection() {
		return clientSelection;
	}

	/**
	 * Sets the client selection.
	 *
	 * @param clientSelection the new client selection
	 */
	public void setClientSelection(ClientSelection clientSelection) {
		this.clientSelection = clientSelection;
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
	 * Gets the ignore case.
	 *
	 * @return the ignore case
	 */
	public Boolean getIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * Sets the ignore case.
	 *
	 * @param ignoreCase the new ignore case
	 */
	public void setIgnoreCase(Boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}

	/**
	 * Gets the parameter name.
	 *
	 * @return the parameter name
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * Sets the parameter name.
	 *
	 * @param parameterName the new parameter name
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * Gets the constraint key.
	 *
	 * @return the constraint key
	 */
	public String getConstraintKey() {
		return constraintKey;
	}

	/**
	 * Sets the constraint key.
	 *
	 * @param constraintKey the new constraint key
	 */
	public void setConstraintKey(String constraintKey) {
		this.constraintKey = constraintKey;
	}

	/**
	 * Gets the value object.
	 *
	 * @return the value object
	 */
	public ValueObject getValueObject() {
		return valueObject;
	}

	/**
	 * Sets the value object.
	 *
	 * @param valueObject the new value object
	 */
	public void setValueObject(ValueObject valueObject) {
		this.valueObject = valueObject;
	}

	/**
	 * Gets the value objects.
	 *
	 * @return the value objects
	 */
	public List<ValueObject> getValueObjects() {
		return valueObjects;
	}

	/**
	 * Sets the value objects.
	 *
	 * @param valueObjects the new value objects
	 */
	public void setValueObjects(List<ValueObject> valueObjects) {
		this.valueObjects = valueObjects;
	}

	/**
	 * Gets the display.
	 *
	 * @return the display
	 */
	public List<Object> getDisplay() {
		return display;
	}

	/**
	 * Sets the display.
	 *
	 * @param display the new display
	 */
	public void setDisplay(List<Object> display) {
		this.display = display;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ClientConstraint [combinationType=" + combinationType + ", clientSelection=" + clientSelection
				+ ", operator=" + operator + ", expression=" + expression + ", value=" + value + ", valueType="
				+ valueType + ", values=" + values + ", ignoreCase=" + ignoreCase + "]";
	}

}