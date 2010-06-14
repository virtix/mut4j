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

package org.apache.log4j.jdbcplus.examples.test;

import java.sql.Driver;
import java.sql.DriverManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.jdbcplus.JDBCAppender;

//*******************************************************************************
// Here is a code example using the JDBCSqlHandler
public class Log4JTest {
	// Create a category instance for this class
	static Logger logger = Logger.getLogger(Log4JTest.class);

	public static void main(String[] args) {
		// Ensure to have all necessary drivers installed !
    // alternative: ja.setDbclass("oracle.jdbc.driver.OracleDriver");
		try {
			Driver d =
				(Driver) (Class
					.forName("oracle.jdbc.driver.OracleDriver")
					.newInstance());
			DriverManager.registerDriver(d);
		} catch (Exception e) {
			/* empty */
		}

		// Set the priority which messages have to be logged
		logger.setLevel(Level.DEBUG);

		// Create a new instance of JDBCAppender
		JDBCAppender ja = new JDBCAppender();

		// Set options with method setOption()
		ja.setConnector("org.apache.log4j.jdbcplus.examples.OracleConnectionHandler");
		ja.setUrl(
			"jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(Host=LENZI)(Port=1521))(ADDRESS=(COMMUNITY=tcp.world)(PROTOCOL=TCP)(Host=LENZI)(Port=1526)))(CONNECT_DATA=(SID=LENZI)))");
		ja.setUsername("mex_pr_dev65");
		ja.setPassword("mex_pr_dev65");

		ja.setSqlhandler("org.apache.log4j.jdbcplus.examples.SqlHandler");

		// other options
		//ja.setBuffer("1");
		//ja.setCommit("Y");

		// Add the appender to a category
		logger.addAppender(ja);

		// These messages with Priority >= setted priority will be logged to the database.
		logger.debug("debug'");
		logger.info("info");
		logger.error("error");
		logger.fatal("fatal");
	}
}
