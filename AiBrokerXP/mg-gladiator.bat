rem in TaskScheduler set
rem Program/script: cmd
rem Add arguments: /C start "MarketGlory.Gladiator" /min F:\AiBrokerXP\mg-gladiator.bat
rem Start in: F:\AiBrokerXP

echo Running MarkerGlory Operator
rem @echo off
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar ess.mg.agents.gladiator.MgGladiator
exit
