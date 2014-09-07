rem in TaskScheduler set
rem Program/script: cmd
rem Add arguments: /K  F:\AiBrokerXP\aibroker-eod-update.bat
rem Start in: F:\AiBrokerXP

rem @echo off
echo Updating EOD REGS
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar aibroker.agents.manager.QuotesManager -eodupdate
