import java.sql.Connection;
import java.sql.DriverManager;

public class JavaConnector {
	public static Connection ConnectDb () {
		String jdbcUrl = "jdbc:mysql://localhost:8888/sdptsdb?useSSL=false";
		String user = "root";
		String pass = "";
		
		try {
			System.out.println("Connecting to database: " + jdbcUrl);
		
			Connection myConn =
				DriverManager.getConnection(jdbcUrl, user, pass);
			
			System.out.println("Connection successful!");
			
			return myConn;
		} catch (Exception exc) {
			exc.printStackTrace();
			return  null;
		}	
	}
}
