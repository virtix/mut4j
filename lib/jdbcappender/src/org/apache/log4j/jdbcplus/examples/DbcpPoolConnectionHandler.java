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
import java.sql.SQLException;

import org.apache.log4j.jdbcplus.JDBCPoolConnectionHandler;

/**
 * Implement a sample JDBCConnectionHandler using a Jakarta commons-dbcp connection pool
 * 
 * @author 
 * <a href="http://www.mannhaupt.com/danko/contact/">Danko Mannhaupt</a>
 * @since 2.0
 * @version see jdbcappender.jar/META-INF/MANIFEST.MF for version information
 */
public class DbcpPoolConnectionHandler implements JDBCPoolConnectionHandler {
	static {
		try {
      // set XML properties
      System.getProperties().put("org.xml.sax.driver", "org.apache.xerces.parsers.SAXParser");
			// load driver
			Driver dDbcp =
				(Driver) (Class.forName("org.apache.commons.dbcp.PoolingDriver").newInstance());
			DriverManager.registerDriver(dDbcp);
      Driver dMySql =
        (Driver) (Class.forName("com.mysql.jdbc.Driver").newInstance());
      DriverManager.registerDriver(dMySql);
		} catch (Exception e) {
			System.err.println("Could not register driver.");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn =
				DriverManager.getConnection(
					"jdbc:apache:commons:dbcp:/org/apache/log4j/jdbcplus/examples/dbcp");
		} catch (SQLException exception) {
			System.err.println("Could not get connection.");
			exception.printStackTrace();
		}
		return conn;
	}

	public Connection getConnection(String _url, String _username, String _password) {
		return getConnection();
	}

	/**
	 * The connection is free again, and can be used elsewhere
	 * @param  con connection to be freed
	 * @exception Exception if any error occurs
	 */
	public void freeConnection(Connection con) throws Exception {
		con.close();
	}
}
