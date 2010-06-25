package edu.gmu.mut4j;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

import net.sf.log4jdbc.ConnectionSpy;
import net.sf.log4jdbc.DriverSpy;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;


public class JDBCLogTest {

	static Logger logger = Logger.getLogger("jdbc");
	@Before
	public void init(){
		PropertyConfigurator.configure("/home/billy/workspace/mut4j/bin/edu/gmu/mut4j/log4j.properties");
		//BasicConfigurator.configure();	
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
		 while (rs.next()){
			 //logger.info( rs.getString("id") + " : " + rs.getString("data"));
		 }
		 
	}
	
	
	
	@Test
	public void complexSQL() throws Exception {
		Class.forName("net.sf.log4jdbc.DriverSpy");
		Connection conn = DriverManager.getConnection("jdbc:log4jdbc:hsqldb:hsql://localhost/", "SA", "");	
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from example e, example f where e.id=f.id and (id > 0 OR id < 99) or (id=1 and id > 0);");
	}
	
	
}
