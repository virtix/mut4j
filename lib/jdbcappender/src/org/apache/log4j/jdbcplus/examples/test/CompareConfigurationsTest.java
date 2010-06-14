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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

// Here is a example using the DOMConfigurator :

public class CompareConfigurationsTest extends TestCase {

    private static final int COUNTER_MAX = 30;

    // Create a category instance for this class
    static Logger logger = null;

    public void testPropertyConfiguratorSql() throws Exception {
        System.out.println("testPropertyConfiguratorSql");
        PropertyConfigurator
                .configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j.properties");

        this.doLogging();
    }

    public void testDomConfiguratorPrepStmt() throws Exception {
        System.out.println("testDomConfiguratorPrepStmt");
        DOMConfigurator
                .configure("./src/org/apache/log4j/jdbcplus/examples/test/log4j_test_prepstmt.xml");

        this.doLogging();
    }

    /**
     * actual log statements
     * 
     * @throws Exception
     */
    private void doLogging() throws Exception {
        logger = Logger.getLogger(this.getClass());

        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNTER_MAX; i++) {
            logger.info("info MyNDC");
            MDC.put("MyMDC2", new Exception("MDC2"));
            logger.info("info MyNDC MyMDC", new Exception("Exception"));
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("time: " + time);

    }
}