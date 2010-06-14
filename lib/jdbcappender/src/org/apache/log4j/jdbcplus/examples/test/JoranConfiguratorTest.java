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

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.apache.log4j.joran.JoranConfigurator;
import org.apache.log4j.spi.LoggerRepository;

/*
 * JUST DELETE IF NOT NEEDED.
 * Here is a example using the JoranConfigurator.
 * Only works with Log4j 1.3. 
 */
public class JoranConfiguratorTest extends TestCase {

    // Create a category instance for this class
    static Logger logger = Logger.getLogger(JoranConfiguratorTest.class.getName());

    public void testJoranConfigurator() throws Exception {
        LoggerRepository lrWrite = new Hierarchy(Logger.getRootLogger());
        lrWrite.resetConfiguration();

        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.doConfigure(
                "./src/org/apache/log4j/jdbcplus/examples/test/log4j_test.joran.xml", lrWrite);

        this.doLogging();
    }

    public void testJoranConfiguratorSimple() throws Exception {
        LoggerRepository lrWrite = new Hierarchy(Logger.getRootLogger());
        lrWrite.resetConfiguration();

        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.doConfigure(
                "./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_simple.joran.xml",
                lrWrite);

        this.doLogging();
    }

    public void testAsync() throws Exception {
        LoggerRepository lrWrite = new Hierarchy(Logger.getRootLogger());
        lrWrite.resetConfiguration();

        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.doConfigure(
                "./src/org/apache/log4j/jdbcplus/examples/test/log4j_async.joran.xml", lrWrite);

        for (int i = 0; i < 1; i++) {
            this.doLogging();
        }
    }

    public void testJoranConfiguratorPreparedStatements() throws Exception {
        LoggerRepository lrWrite = new Hierarchy(Logger.getRootLogger());
        lrWrite.resetConfiguration();

        JoranConfigurator joranConfigurator = new JoranConfigurator();
        joranConfigurator.doConfigure(
                "./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_prepstmt.joran.xml",
                lrWrite);

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
        MDC.remove("MyMDC");
    }
}