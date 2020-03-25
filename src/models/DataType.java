package models;

/**
 * The data type of a physical or logical column.
 * 
 * @author Raju Alluri
 * 
 */
public enum DataType {
	// UNKNOWN(0, "UNKNOWN"), STRING(1, "STRING"), DATE(2, "DATE"), DATETIME(2,
	// "DATETIME"), BOOLEAN(3, "BOOLEAN"), INTEGER(4, "INTEGER"), DOUBLE(5,
	// "FLOAT");

	/** The unknown. */
	UNKNOWN("UNKNOWN"),
	/** The string. */
	STRING("STRING"),
	/** The date. */
	DATE("DATE"),
	/** The datetime. */
	DATETIME("DATETIME"),
	/** The boolean. */
	BOOLEAN("BOOLEAN"),
	/** The integer. */
	INTEGER("INTEGER"),

	/** The decimal. */
	DECIMAL("DECIMAL");

	/** The name. */
	// private int type;
	private final String name;

	/**
	 * Instantiates a new data type.
	 *
	 * @param name the name
	 */
	DataType(String name) {
		// this.type = type;
		this.name = name;
	}

	// public int getType() {
	// return type;
	// }
	//
	// public void setType(int type) {
	// this.type = type;
	// }

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
