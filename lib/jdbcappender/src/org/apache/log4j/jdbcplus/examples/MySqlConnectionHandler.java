/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.log4j.jdbcplus.examples;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import org.apache.log4j.jdbcplus.JDBCConnectionHandler;

/**
 * Implement a sample JDBCConnectionHandler for MySQL database
 * 
 * @author 
 * <a href="http://www.mannhaupt.com/danko/contact/">Danko Mannhaupt</a>
 * @since 2.0
 * @version see jdbcappender.jar/META-INF/MANIFEST.MF for version information
 */ 
public class MySqlConnectionHandler implements JDBCConnectionHandler {
  Connection con = null;
  String url = "jdbc:mysql://localhost/danko?user=danko&password=";
  String username = "danko";
  String password = "danko";

  static {
	  try {
		  // load driver
			Driver dMySql = (Driver) (Class.forName("com.mysql.jdbc.Driver").newInstance());
	  	DriverManager.registerDriver(dMySql);
	  } catch (Exception e) {
		  System.err.println("Could not register driver.");
		  e.printStackTrace();
	  }
  }

  public Connection getConnection() {
    return getConnection(url, username, password);
  }

  public Connection getConnection(String _url, String _username, String _password) {
    try {
      if (con != null && !con.isClosed())
        con.close();
			con = DriverManager.getConnection(url);
      con.setAutoCommit(false);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return con;
  }
}
