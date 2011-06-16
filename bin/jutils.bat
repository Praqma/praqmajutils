
@echo OFF

set program=%1
set args=%*
set package=net.praqma.cli

IF NOT "%program%"=="" GOTO programok

echo.
echo The program was not given
echo.

EXIT /B 1

:programok

IF NOT "%JUTILS_HOME%"=="" GOTO JUTILShomeok

echo.
echo JUTILS_HOME is not set
echo.

EXIT /B 1

:JUTILShomeok

set JUTILS_JAR=%JUTILS_HOME%\build\praqmajutils.jar

if exist "%JUTILS_JAR%" goto start


echo.
echo %JUTILS_JAR% was not found
echo.

EXIT /B 1

:start

set JUTILS=java -classpath %JUTILS_JAR% %package%.%program% %args%

rem echo %JUTILS%

call %JUTILS%


@GOTO :EOF


:WRONG_PARAMS
ECHO %1 is missing


EXIT /B 
GOTO :EOF