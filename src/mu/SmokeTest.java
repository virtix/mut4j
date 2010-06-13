package mu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;


public class SmokeTest {

	@Test
	public void connectAndRun() throws Exception {
		 Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");	
		 Statement stmt = c.createStatement();
		 ResultSet rs = stmt.executeQuery("select * from example;");
		 while (rs.next()){
			 System.out.println( rs.getString("id") + " : " + rs.getString("data"));
		 }
		 
	}
	
	@Test
	public void connectAndRunAndLog() throws Exception {
		 Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");	
		 Statement stmt = c.createStatement();
		 ResultSet rs = stmt.executeQuery("select * from example;");
		 while (rs.next()){
			 System.out.println( rs.getString("id") + " : " + rs.getString("data"));
		 }
		 
	}
	
}
