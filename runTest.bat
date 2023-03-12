@echo off
cls
echo Wait, please!
call mvn -B -X test > test.txt 2>test.err.txt
type test.txt | c:\windows\system32\find "BUILD FAILURE"
IF ERRORLEVEL == 1 GOTO OK
echo ERROR
goto QUIT
:OK
echo OK
:QUIT