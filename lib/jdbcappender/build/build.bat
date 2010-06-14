@echo off
setlocal

set ANT_HOME=D:\Programme\Java\apache\ant\apache-ant-1.6.1
set PATH=%PATH%;%ANT_HOME%\bin

echo ========
echo .
echo JDK 1.3
echo compile fails for some classes for JDK 1.3
set JAVA_HOME=D:\Programme\Java\sdk\j2se\jdk1.3.1_10
call ant compile %2 %3

echo ========
echo .
echo JDK 1.5
set JAVA_HOME=D:\Programme\Java\sdk\j2se\j2sdk1.5.0
call ant compile %2 %3

echo ========
echo .
echo Default and final: JDK 1.4
set JAVA_HOME=C:\Programme\Java\j2se\j2sdk1.4.1_01
call ant %1 %2 %3

endlocal