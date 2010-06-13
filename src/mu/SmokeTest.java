package mu;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import net.sf.log4jdbc.ConnectionSpy;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.jdbcdslog.ConnectionLoggingProxy;
import org.junit.Before;
import org.junit.Test;


public class SmokeTest {

//	@Test
	public void connectAndRunWithLog4JDBCDS() throws Exception {
		 Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");	
		 Statement stmt = c.createStatement();
		 ResultSet rs = stmt.executeQuery("select * from example;");
		 while (rs.next()){
			 System.out.println( rs.getString("id") + " : " + rs.getString("data"));
		 }
		 
	}


	static Logger logger = Logger.getLogger("mut4j");
	
	@Before
	public void init(){
		PropertyConfigurator.configure("/home/billy/workspace/mut4j/bin/mu/log4j.properties");
		//BasicConfigurator.configure();	
	}
	
	@Test
	public void connectAndRunJDBCDSLog() throws Exception {
		
		 Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");	
		 //Example 1 change to code
		 Connection loggingConnection = ConnectionLoggingProxy.wrap(conn);
		 Statement stmt = loggingConnection.createStatement();
		 ResultSet rs = stmt.executeQuery("select * from example where 1=1;");
		 while (rs.next()){
			 //logger.info( rs.getString("id") + " : " + rs.getString("data"));
		 }
		 
		 PreparedStatement pstmt = loggingConnection.prepareStatement("select * from example where ID=?");
		 
	}
	
	
	@Test
	public void connectAndRunJDBCLog() throws Exception {
		//jdbc:jdbcdslog:hsqldb:mem:mymemdb;targetDriver=org.hsqldb.jdbcDriver
		 Connection conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "SA", "");	
		 //Example 1 change to code
		 Connection loggingConnection = new ConnectionSpy(conn);
		 Statement stmt = loggingConnection.createStatement();
		 ResultSet rs = stmt.executeQuery("select * from example where 1=1");
		 PreparedStatement pstmt = loggingConnection.prepareStatement("select * from example where ID=?");
		 pstmt.setString(1, "1");
		 rs = pstmt.executeQuery();
		 
		 
	}
	
}
