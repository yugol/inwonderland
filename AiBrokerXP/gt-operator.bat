rem in TaskScheduler set
rem Program/script: cmd
rem Add arguments: /C start "GoalTycoon.Operator" /min F:\AiBrokerXP\gt-operator.bat
rem Start in: F:\AiBrokerXP

echo Running GoalTycoon Operator
rem @echo off
set AIBROKERXP_HOME=%~dp0
set JAVA="java.exe"
cd "%AIBROKERXP_HOME%"
%JAVA% -cp aibroker.jar ess.gt.agents.operator.GtOperator
exit
