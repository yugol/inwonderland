rem in TaskScheduler set
rem Program/script: cmd
rem Add arguments: /C start "MarketGlory.Trader" /min F:\AiBrokerXP\mg-trader.bat
rem Start in: F:\AiBrokerXP

echo Running MarkerGlory Trader
rem @echo off
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar ess.mg.agents.trader.MgTrader
exit
