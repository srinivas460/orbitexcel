package models;

public enum Alignment {
	LEFT("left"), RIGHT("right"), CENTERED("center"), JUSTIFIED("justified");

	private String code;

	Alignment(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
