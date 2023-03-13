@echo off
call mvn surefire-report:report
start .\target\site\surefire-report.html