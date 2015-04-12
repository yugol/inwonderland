rem in TaskScheduler set
rem Program/script: cmd
rem Add arguments: /C start "MarketGlory.Operator" /min F:\AiBrokerXP\mg-operator.bat
rem Start in: F:\AiBrokerXP

echo Running MarkerGlory Operator
rem @echo off
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar ess.mg.agents.operator.Operator
exit
