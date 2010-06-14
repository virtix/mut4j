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

import junit.framework.TestSuite;

/**
 * test suite
 */
public class PackageTest extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite();
		
		suite.addTestSuite(JdbcAppenderTest.class);
		suite.addTestSuite(DomConfiguratorTest.class);
		
		return suite;
	}
  
}
