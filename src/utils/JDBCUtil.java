package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	public static Connection getDriverConnection(String driver, String url, String user, String pass) throws Exception {
		Class.forName(driver);
		return DriverManager.getConnection(url, user, pass); 
	}

	public static void close(Connection conn) throws SQLException {
	    close(null, null, conn);
	}
	
	public static void close(Statement stmt) throws SQLException {
	    close(null, stmt, null);
	}
	
	public static void close(ResultSet rs) throws SQLException {
	    close(rs, null, null);
    }
	
	public static void close(Statement stmt, Connection conn) throws SQLException {
	    close(null, stmt, conn);
    }
	
	public static void close(ResultSet rs, Statement stmt) throws SQLException {
	    close(rs, stmt, null);
	}
	
	public static void close(ResultSet rs, Statement stmt, Connection conn) throws SQLException {
	  
	    if (null != rs) {
            rs.close();
            rs = null;
        }
        
        if(null != stmt) {
            stmt.close();
            stmt = null;
        }
        
        if(null != conn) {
            conn.close();
            conn = null;
        }
	}
	
}