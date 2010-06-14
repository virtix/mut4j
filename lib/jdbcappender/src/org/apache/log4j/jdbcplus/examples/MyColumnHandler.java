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

import org.apache.log4j.jdbcplus.*;
import org.apache.log4j.spi.LoggingEvent;

/**
 * simple column handler example
 * 
 * @author 
 * <a href="mailto:t.fenner@klopotek.de">Thomas Fenner</a>
 * @since 1.0
 * @version see jdbcappender.jar/META-INF/MANIFEST.MF for version information
 */
public class MyColumnHandler implements JDBCColumnHandler {

    /**
     *  
     */
    public Object getObject(LoggingEvent event, String table, String column) throws Exception {
        Object result = null;
        Object eventMessage = event.getMessage();
        // could extract anything from LoggingEvent here
        // for instance logged objects:
        result = eventMessage;
        // could cast and extract instance information here
        // apply to your own types
        if (eventMessage instanceof Exception) {
            result = ((Exception) eventMessage).getLocalizedMessage();
        }

        return result;
    }
}