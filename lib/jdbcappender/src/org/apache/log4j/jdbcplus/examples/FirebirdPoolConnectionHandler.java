/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.log4j.jdbcplus.examples;

import java.sql.Connection;

import org.apache.log4j.jdbcplus.JDBCPoolConnectionHandler;

/**
 * Custom JDBCPoolConnectionHandler to handle connection management for a
 * Firebird database
 * 
 * @author 
 * <a href="http://www.mannhaupt.com/danko/contact/">Danko Mannhaupt</a>
 * @since 2.0
 * @version see jdbcappender.jar/META-INF/MANIFEST.MF for version information
 */
public class FirebirdPoolConnectionHandler implements JDBCPoolConnectionHandler {

    private static org.firebirdsql.pool.FBWrappingDataSource fbwds = null;

    static {
        try {
            /*
             * // load driver Class.forName("org.firebirdsql.jdbc.FBDriver"); //
             * try to get connection directly Connection conn =
             * DriverManager.getConnection(
             * "jdbc:firebirdsql:localhost/3050:C:\\Programme\\Firebird\\Data\\db.gdb",
             * "danko", "danko"); conn.close();
             */

            // initialize pool
            fbwds = new org.firebirdsql.pool.FBWrappingDataSource();

            // created db alias in aliases.conf: danko =
            // D:\Programme\Firebird\Firebird1.5\Data\danko.fdb
            fbwds.setDatabase("localhost/3050:danko");
            fbwds.setUserName("danko");
            fbwds.setPassword("danko");
            fbwds.setMaxIdleTime(30);
            fbwds.setPooling(true);
            // this turns on pooling for this data source. Max and min must be
            // set.
            fbwds.setMinPoolSize(2);
            // this sets the minimum number of connections to keep in the pool
            fbwds.setMaxPoolSize(5);
            // this sets the maximum number of connections that can be open at
            // one time.
            fbwds.setLoginTimeout(10);
        } catch (Exception e) {
            System.err.println("Could not register firebird driver.");
            e.printStackTrace();
        }
    }

    /**
     * Get a connection
     * 
     * @return The Connection value
     * @exception Exception
     *                Description of Exception
     */
    public Connection getConnection() throws Exception {
        Connection conn = null;

        conn = FirebirdPoolConnectionHandler.fbwds.getConnection();
        conn.setAutoCommit(false);

        return conn;
    }

    /**
     * Get a defined connection
     * 
     * @param _url
     *            Description of Parameter
     * @param _username
     *            Description of Parameter
     * @param _password
     *            Description of Parameter
     * @return The Connection value
     * @exception Exception
     *                Description of Exception
     */
    public Connection getConnection(String _url, String _username,
            String _password) throws Exception {
        return this.getConnection();
    }

    /**
     * The connection is free again, and can be used elsewhere
     * 
     * @param con
     *            connection to be freed
     * @exception Exception
     *                if any error occurs
     */
    public void freeConnection(Connection con) throws Exception {
        con.close();
    }

}