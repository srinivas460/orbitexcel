package models;

public class Log {

	public static void debug(String msg) {
		System.out.println(msg);
	}
	public static void error(String msg) {
		System.err.println(msg);
	}

	public static void debug(String string, String error) {
		System.out.print(string+error);
	}
	public static void error(String string, String error) {
		System.err.print(string+error);
	}

}
