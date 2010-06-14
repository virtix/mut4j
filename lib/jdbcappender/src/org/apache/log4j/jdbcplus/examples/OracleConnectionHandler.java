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
import java.sql.DriverManager;

import org.apache.log4j.jdbcplus.JDBCConnectionHandler;

/**
 * Implement a sample JDBCConnectionHandler for Oracle database
 * 
 * @author 
 * <a href="http://www.mannhaupt.com/danko/contact/">Danko Mannhaupt</a>
 * @since 2.0
 * @version see jdbcappender.jar/META-INF/MANIFEST.MF for version information
 */ 
public class OracleConnectionHandler implements JDBCConnectionHandler {
  Connection con = null;
  //Default connection
  String url =
    "jdbc:oracle:thin:@"
      + "(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 62.52.100.41)(PORT = 1521)))" +        "(CONNECT_DATA = (SERVICE_NAME = oracle8i)))";
  String username = "danko";
  String password = "danko";

  public Connection getConnection() {
    return getConnection(url, username, password);
  }

  public Connection getConnection(String _url, String _username, String _password) {
    try {
      if (con != null && !con.isClosed())
        con.close();
      con = DriverManager.getConnection(_url, _username, _password);
      con.setAutoCommit(false);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return con;
  }
}
