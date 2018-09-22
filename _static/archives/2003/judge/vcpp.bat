@echo off
rem vcpp.bat  compiles Visual C++ and C  (See usage at the end.)
rem The following set lines work on Loyola's VC++ 7.1:
rem Alter the first line starting with "set" for your individual setup.

if "%2z" == "z" goto usage
if not "%3z" == "z" goto usage
if not "%VCPPBASE%z" == "z" goto compile

set VSNETBASE=C:\Program Files\Microsoft Visual Studio .NET 2003
set VCPPBASE=%VSNETBASE%\Vc7
set path=%path%;%VCPPBASE%\Bin;%VSNETBASE%\Common7\IDE

:compile

"%VCPPBASE%\Bin\cl" /I"%VCPPBASE%\include" /I"%VCPPBASE%\ATLmfc\include" /nologo /MLd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /FD /GZ /c %1.%2

"%VCPPBASE%\Bin\link" %1.obj /nologo /LIBPATH:"%VCPPBASE%\lib"  /subsystem:console

goto end

:usage
echo Usage:
echo   vcpp filebase ext
echo      where ext is c or cpp 
echo      as in 
echo   vcpp  myprog  cpp
echo      to compile myprog.cpp and link myprog.obj into myprog.exe. 

:end
