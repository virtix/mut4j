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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

//import org.apache.log4j.jdbcplus.JDBCConnectionHandler;
import org.apache.log4j.jdbcplus.JDBCPoolConnectionHandler;
//import org.apache.log4j.jdbcplus.examples.DbcpPoolConnectionHandler;
import org.apache.log4j.jdbcplus.examples.FirebirdPoolConnectionHandler;
//import org.apache.log4j.jdbcplus.examples.MySqlConnectionHandler;

/**
 * Test Case for comparison of prepared vs. non-prepared statements
 * @author mann17, $Author:   mann17  $
 * @version $Revision:   1.0  $
 */
public class PreparedStatementTest extends TestCase {
	private static final int COUNTER_MAX = 100;
	private static final String NON_PREPARED_SQL =
		"INSERT INTO LOG_LOG4J2 (MSG) VALUES ('test non prepared') ";
	private static final String PREPARED_SQL = "INSERT INTO LOG_LOG4J2 (MSG) VALUES (?) ";
	private static final String PREPARED_VALUE = "test yes prepared";
	private JDBCPoolConnectionHandler poolConnHandler = new FirebirdPoolConnectionHandler();
	// private JDBCPoolConnectionHandler poolConnHandler = new DbcpPoolConnectionHandler();
	// private JDBCConnectionHandler connHandler = new MySqlConnectionHandler();
	private Connection conn = null;

	static {
		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver");
		} catch (Exception e) {
			System.err.println("Could not register firebird driver.");
			e.printStackTrace();
		}
	}

	/**
	 * main operation.
	 * @param args arguments
	 */
	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	/**
	 * @return test suite
	 */
	public static Test suite() {
		return new TestSuite(PreparedStatementTest.class);
	}

	public void setUp() throws Exception {
		conn = poolConnHandler.getConnection();
		// conn = connHandler.getConnection();
	}

	public void tearDown() throws Exception {
		poolConnHandler.freeConnection(conn);
	}

	public void testNonPrepared() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < COUNTER_MAX; i++) {
			Statement stmt = conn.createStatement();
			int insertedCounter = stmt.executeUpdate(NON_PREPARED_SQL);
			assertEquals(1, insertedCounter);
			stmt.close();
			conn.commit();
		}
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("non prepared time: " + time);
	}

	public void testPrepared() throws Exception {
		long start = System.currentTimeMillis();
		PreparedStatement stmt = conn.prepareStatement(PREPARED_SQL);
		for (int i = 0; i < COUNTER_MAX; i++) {
			stmt.setString(1, PREPARED_VALUE);
			int insertedCounter = stmt.executeUpdate();
			assertEquals(1, insertedCounter);
			conn.commit();
		}
		stmt.close();
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("prepared time: " + time);
	}
}