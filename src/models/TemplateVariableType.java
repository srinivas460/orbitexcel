package models;

public enum TemplateVariableType {

	ParameterStyle("parameterStyle"), ReportName("ReportName"), ReportHeader("ReportHeader"), TableHeader("TableHeader"), ReportData("ReportData"),
	ReportInfo("ReportInfo"), GeneratedDate("GeneratedDate"), ParametersHeader("ParametersHeader"),
	ParametersData("ParametersData"), ParameterInfo("ParameterInfo"), SheetRawData("RawData"),
	SheetDetails("ReportDetails"), SheetOutput("ReportOutput"), SheetPivotConfig("PivotOrbConfig"),TableHeaderStyle("TableHeaderStyle"),
	USER_INFO_SETTINGS("user_info_settings");

	/** The value. */
	private final String value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Instantiates a new publish type.
	 *
	 * @param value the value
	 */
	TemplateVariableType(String value) {
		this.value = value;
	}
}
