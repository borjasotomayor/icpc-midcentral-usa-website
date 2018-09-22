@echo off
cd %1
if not exist %1.exe goto java
echo running %1.exe
if "%2z"=="z" goto exe2scr
echo output to %1%2
%1 > %1%2
goto doneexe
:exe2scr
%1
:doneexe
echo done running %1.exe
:java
if not exist %1.class goto done
echo running java %1
if "%2z"=="z" goto java2scr
echo output to %1%2
java %1 > %1%2
goto donejava
:java2scr
java %1
:donejava
echo done running java %1

:done
cd ..