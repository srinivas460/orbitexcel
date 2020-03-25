package utils;

public class Logs {


	public static void debug(String message) {
		System.out.println(message );
	}
	public static void error(String message) {
		System.err.println(message);
	}
	public static void debug(String message,Object eror) {
		System.out.println(message+(eror!=null?eror:""));
	}
	public static void error(String message,Object eror) {
		System.err.println(message+(eror!=null?eror:""));
	}
}
