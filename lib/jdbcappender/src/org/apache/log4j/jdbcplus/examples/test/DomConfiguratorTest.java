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

package org.apache.log4j.jdbcplus.examples.test;

import java.rmi.RemoteException;
import java.sql.Timestamp;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.apache.log4j.xml.DOMConfigurator;

/*
 * Here is a example using the DOMConfigurator
 */ 
public class DomConfiguratorTest extends TestCase {

    // Create a category instance for this class
    static Logger logger = Logger.getLogger(DomConfiguratorTest.class.getName());

    public void testDomConfigurator() throws Exception {

        // Configuration with configuration-file
        DOMConfigurator.configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_test.xml");

        this.doLogging();
    }

    public void testDomConfiguratorSimple() throws Exception {
        // Configuration with configuration-file
        // DOMConfigurator.configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_threshold.xml");
        DOMConfigurator
                .configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_simple.xml");

        this.doLogging();
    }

    public void testAsync() throws Exception {
        // Configuration with configuration-file
        DOMConfigurator.configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_async.xml");

        for (int i = 0; i < 1; i++) {
            this.doLogging();
        }
    }

    /**
     * Prepared Statement test
     * @throws Exception
     */
    public void testDomConfiguratorPreparedStatements() throws Exception {
        // Configuration with configuration-file
        DOMConfigurator
                .configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_prepstmt.xml");
        this.doLogging();
    }

    /**
     * Stored Procedure Test. 
     * Requires latest Firebird JDBC driver. 
     * As of 22.08.2004 only available as source code from CVS repository.
     * Requires J2SDK 1.4.
     * @throws Exception
     */
    public void testDomConfiguratorStoredProcedure() throws Exception {
        // Configuration with configuration-file
        DOMConfigurator
                .configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_storedproc.xml");
        this.doLogging();
    }

    /**
     * actual log statements
     * 
     * @throws Exception
     */
    private void doLogging() throws Exception {
        // These messages with Level >= set level will be logged to
        // the database.
        logger.debug("debug DOM");
        // this not, because Priority DEBUG is less than INFO
        logger.info("info'test DOM");
        logger.error("nested nullpointer error", new RemoteException("test", new RemoteException(
                "test2", new NullPointerException())));
        logger.fatal("fatal DOM");

        // NDC/MDC
        logger.info(new Timestamp(1)); //object logging
        NDC.push("MyNDC");
        logger.info("info MyNDC");
        MDC.put("MyMDC", "MyMDC");
        MDC.put("MyMDC2", new Exception("MDC2").toString());
        logger.info("info MyNDC MyMDC");
        NDC.pop();
        logger.info("info MyMDC");

        // null tests
        logger.fatal(null);
        
        MDC.remove("MyMDC");
    }
}