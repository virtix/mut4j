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

import java.sql.Date;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import org.apache.log4j.jdbcplus.JDBCLogger;
import org.apache.log4j.jdbcplus.JDBCSqlHandler;

/**
 * Custom JDBC SQLHandler to handle statement creation for log statements
 * 
 * @author 
 * <a href="http://www.mannhaupt.com/danko/contact/">Danko Mannhaupt</a>
 * @since 2.0
 * @version see jdbcappender.jar/META-INF/MANIFEST.MF for version information
 */
public class SqlHandler implements JDBCSqlHandler {

    private final int MAX_LENGTH_MESSAGE = 3000;

    /**
     * return database SQL statement to log logging event
     * 
     * @param event
     *            the event
     * @return SQL insert statement
     * @throws Exception
     *             if any error occurs
     * @see org.apache.log4j.jdbcplus.JDBCSqlHandler#getStatement(LoggingEvent)
     */
    public String getStatement(LoggingEvent event) throws Exception {
        // try { throw new Throwable(); } catch (Throwable th) {
        // th.printStackTrace(); }
        LocationInfo locinfo = event.getLocationInformation();
        ThrowableInformation throwableinfo = event.getThrowableInformation();
        StringBuffer throwableStringBuffer = new StringBuffer();
        String locinfoString = "'', '', '', ''";
        String statement = "";

        if (locinfo != null) {
            locinfoString = "'" + locinfo.getClassName() + "', '" + locinfo.getMethodName()
                    + "', '" + locinfo.getFileName() + "', '" + locinfo.getLineNumber() + "'";
        }
        if (throwableinfo != null) {
            String[] lines = throwableinfo.getThrowableStrRep();
            for (int index = 0; index < lines.length; index++) {
                throwableStringBuffer = (StringBuffer) throwableStringBuffer.append(lines[index]
                        + "\r\n");
            }
        }

        statement = "INSERT INTO LOG_LOG4J (LOGDate, Logger, Priority, "
                + "Loc_ClassName, Loc_MethodName, Loc_FileName, Loc_LineNumber, Msg, Throwable) VALUES ( ";
        statement = statement + "'" /* Log4j 1.2.8 */+ new Date(event.timeStamp)
        /* Log4j 1.3 *//* + new Date(event.getTimeStamp()) */+ "', '" + event.getLoggerName()
                + "', '" + event.getLevel().toString() + "', " + locinfoString + ", '"
                + this.replaceProblematicChars(
                        event.getMessage() == null ? "null" : event.getMessage().toString()) + "', '"
                + this.replaceProblematicChars(throwableStringBuffer.toString()) + "'  )";

        return statement;
    }

    protected String replaceProblematicChars(String aString) {
        String result = new String(aString);
        // use replace function
        result = new JDBCLogger().replace(result, "'", "''");
        if (result.length() > MAX_LENGTH_MESSAGE) {
            result = result.substring(0, MAX_LENGTH_MESSAGE);
        }

        return result;
    }

}