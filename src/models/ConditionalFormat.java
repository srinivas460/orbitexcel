package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConditionalFormat implements Serializable {

	private static final long serialVersionUID = 1L;
	private ClientConstraint condition;
	private ClientSelection formatColumn;
	private Map<String, Object> properties = new HashMap<String, Object>();

	public ClientConstraint getCondition() {
		return condition;
	}

	public void setCondition(ClientConstraint condition) {
		this.condition = condition;
	}

	public ClientSelection getFormatColumn() {
		return formatColumn;
	}

	public void setFormatColumn(ClientSelection formatColumn) {
		this.formatColumn = formatColumn;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public String getStyle() {
		return null;
	}

	public String getStyleClass() {
		return null;
	}

	@Override
	public String toString() {
		return "ConditionalFormat [condition=" + condition + ", formatColumn=" + formatColumn + ", properties="
				+ properties + "]";
	}
	
}
