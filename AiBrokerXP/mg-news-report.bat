rem in TaskScheduler set
rem Program/script: cmd
rem Add arguments: /K  F:\AiBrokerXP\mg-news-report.bat
rem Start in: F:\AiBrokerXP

rem @echo off
echo Updating EOD REGS
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar ess.mg.agents.MgNewsReporter
