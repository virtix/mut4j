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

import java.rmi.RemoteException;
import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.jdbcplus.JDBCAppender;
import org.apache.log4j.jdbcplus.JDBCPoolConnectionHandler;
import org.apache.log4j.jdbcplus.examples.FirebirdPoolConnectionHandler;
import org.apache.log4j.jdbcplus.examples.SqlHandler;

/**
 * test Case for FirebirdPoolConnectionHandler, SqlHandler and JDBC logging
 * @author mann17, $Author:   mann17  $
 * @version $Revision:   1.0  $
 */
public class JdbcAppenderTest extends TestCase {

	static {
		try {
			// Class.forName("org.firebirdsql.jdbc.FBDriver");
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
		return new TestSuite(JdbcAppenderTest.class);
	}

	public void _testGetConnection() throws Exception {
		JDBCPoolConnectionHandler connHandler = new FirebirdPoolConnectionHandler();

		Connection conn = connHandler.getConnection();
		connHandler.freeConnection(conn);
	}

	public void testCustomConfig() throws Exception {
		Logger logger = Logger.getLogger("test");
		JDBCAppender app = new JDBCAppender();
		app.setConnector("org.apache.log4j.jdbcplus.examples.FirebirdPoolConnectionHandler");
		app.setSqlhandler("org.apache.log4j.jdbcplus.examples.SqlHandler");
		app.setLayout(new PatternLayout("%m"));

		logger.addAppender(app);

		logger.setLevel(Level.INFO);
		logger.debug("debug");
		logger.info("info");
		logger.error("error");
		logger.fatal("fatal");
		// null test
		logger.fatal(null);
		logger.fatal(new SqlHandler()); //object logging
	}

	/**
	 * Recovery test. Currently fails :-(
	 * Firebird exception:  
	 * org.firebirdsql.jca.FBResourceTransactionException: 
	 * Local transaction active: can't begin another 
	 * @throws Exception
	 */
	public void _testDbRecovery() throws Exception {
		Logger logger = Logger.getLogger(JdbcAppenderTest.class);

		logger.info("before recovery");
		// manual db dropout here
		logger.info("problem");
		// manual db restart
		logger.info("recovered");
	}

	public void testDefaultConfig() throws Exception {
		Logger logger = Logger.getLogger(JdbcAppenderTest.class);
		logger.debug("de'bug");
		logger.debug(new SqlHandler()); //object logging
		NDC.push("MyNDC");
		logger.debug("debug MyNDC");
		MDC.put("MyMDC", "MyMDC");
		MDC.put("MyMDC2", new Exception("MDC2").toString());
		logger.debug("debug MyNDC MyMDC");
		NDC.pop();
		logger.debug("debug MyMDC");
		MDC.remove("MyMDC");
		// exception with very long stack trace
		logger.debug("ex", this.getVeryLongException(10));

		JDBCAppender app = (JDBCAppender) logger.getParent().getAppender("JDBC2");
		// buffer 2, MDC test (to verify problem report)
		app.setBuffer("2");
		MDC.put("MyMDC", "MDC1");
		logger.debug("debug MyMDC1");
		MDC.put("MyMDC", "MDC2");
		logger.debug("debug MyMDC2");
	}

	public void _testDefaultConfigException() throws Exception {
		Logger logger = Logger.getLogger(JdbcAppenderTest.class);
		logger.debug("nullpointer debug", new NullPointerException());
	}

	public void _testNestedException() throws Exception {
		Logger logger = Logger.getLogger(JdbcAppenderTest.class);
		// using RemoteException because it had nested feature in 1.3, too.
		logger.debug("nested nullpointer debug", new RemoteException("test", new RemoteException(
				"test2", new NullPointerException())));
	}

	/*
	 * recursive exception generation
	 */
	private Exception getVeryLongException(int levels) {
		Exception result = null;
		try {
			this.throwVeryDeepException(levels);
		} catch (Exception e) {
			result = e;
		}
		return result;
	}

	/*
	 * recursive exception generation
	 */
	private void throwVeryDeepException(int levels) throws Exception {
		if (levels == 0) {
			// recursion ends
			throw new Exception("very deep exception");
		} else {
			try {
				this.throwVeryDeepException(levels - 1);
			} catch (Exception e) {
				// nested exception
				throw new Exception("nested", e);
			}
		}
	}
}