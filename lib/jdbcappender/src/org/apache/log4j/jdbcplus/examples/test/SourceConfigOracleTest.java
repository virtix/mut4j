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
import java.sql.Types;
import java.sql.DriverManager;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.jdbcplus.JDBCAppender;
import org.apache.log4j.jdbcplus.JDBCLogType;
import org.apache.log4j.jdbcplus.examples.MyColumnHandler;
import org.apache.log4j.jdbcplus.examples.MyIDHandler;

// Here is a code example to configure the JDBCAppender without a configuration-file using the JDBCIDHandler and JDBCColumnHandler :

public class SourceConfigOracleTest {
  // Create a category instance for this class
  static Logger logger = Logger.getLogger(Log4JTest.class.getName());

  public static void main(String[] args) {
    // A JDBCIDHandler
    MyIDHandler idhandler = new MyIDHandler();

    // A JDBCColumnHandler
    MyColumnHandler colHandler = new MyColumnHandler();

    // Ensure to have all necessary drivers installed !
    try {
      Driver d = (Driver) (Class.forName("oracle.jdbc.driver.OracleDriver").newInstance());
      DriverManager.registerDriver(d);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Set the priority which messages have to be logged
    logger.setLevel(Level.INFO);

    // Create a new instance of JDBCAppender
    JDBCAppender ja = new JDBCAppender();

    // Set options with method setOption()
    ja.setConnector("org.apache.log4j.jdbcplus.examples.OracleConnectionHandler");
    ja.setUrl(
      "jdbc:oracle:thin:@"
        + "(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 62.52.100.41)(PORT = 1521)))"
        + "(CONNECT_DATA = (SERVICE_NAME = oracle8i)))");
    ja.setUsername("danko");
    ja.setPassword("danko");

    ja.setTable("logtest");

    ja.setColumn("id", JDBCLogType.ID, idhandler, "INTEGER", Types.INTEGER);
    ja.setColumn("prio", JDBCLogType.PRIO, "", "VARCHAR", Types.VARCHAR);
    ja.setColumn("cat", JDBCLogType.CAT, "", "VARCHAR", Types.VARCHAR);
    ja.setColumn("msg", JDBCLogType.MSG, "", "VARCHAR", Types.VARCHAR);
    ja.setColumn("info", JDBCLogType.DYNAMIC, colHandler, "VARCHAR", Types.VARCHAR);
    ja.setColumn("addon", JDBCLogType.EMPTY, "", "VARCHAR", Types.VARCHAR);
    ja.setColumn("the_timestamp", JDBCLogType.TIMESTAMP, "", "TIMESTAMP", Types.TIMESTAMP);
    ja.setColumn("created_by", JDBCLogType.STATIC, "ME", "VARCHAR", Types.VARCHAR);

    // other options
    //ja.setBuffer("1");
    //ja.setCommit("Y");

    // Add the appender to a category
    logger.addAppender(ja);

    // These messages with Priority >= setted priority will be logged to the database.
    logger.debug("debug");
    logger.info("info");
    logger.error("error");
    logger.fatal("fatal");

    // not required
    logger.removeAppender(ja);
  }
}
