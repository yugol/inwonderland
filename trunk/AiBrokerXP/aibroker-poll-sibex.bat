rem @echo off
echo Polling SIBX FUTURES quotes
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar aibroker.engines.markets.sibex.SibexFuturesMarketLogger "%AIBROKERXP_HOME%"
