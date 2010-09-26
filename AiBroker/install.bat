@echo off

set ipath=C:\Temp\AiBroker\bin

echo Creating folder %ipath%

mkdir %ipath%

echo Copying binaries to %ipath%

@echo on

@rem copy .\AiBroker.config %ipath%
@rem copy .\TradingAgent\bin\Release\TradingAgent.exe %ipath%
@rem copy .\TradingAgent\order_executed.wav %ipath%
@rem copy .\TradingAgent\order_palced.wav %ipath%
copy .\TradingCommon\bin\Release\TradingCommon.dll %ipath%
copy .\TradingCommon\tradingStatusReport.xsl %ipath%
copy .\TradingDataCenter\bin\Release\TradingDataCenter.exe %ipath%
copy .\TradingSimulator\bin\Release\TradingSimulator.exe %ipath%
copy .\TradingVisualizer\bin\Release\TradingVisualizer.exe %ipath%
@rem copy .\read_daily.bat %ipath%
