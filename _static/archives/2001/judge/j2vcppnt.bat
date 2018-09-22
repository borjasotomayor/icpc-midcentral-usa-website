@echo off
rem ALTERNATE VERSION OF J2.BAT: ------------------------------
rem   The one run at Loyola under NT 4.0 using VC++
rem   If you are using Visual C++, you can rename this j2.bat,
rem   BUT STILL check the base directory set for Visual Basic below.
rem -----------------------------------------------------------
rem j2 problemLetter problemName sourceExt team# 
rem called from j1.bat after source file found
cls
echo Found lettered problem %1: %2.%3 of team %4
echo Copying source file from team diskette...
copy a:\%2.%3 .
echo !----------------------------------------------------------!
echo ! Done copying. Remove the team's diskette from the drive. !
echo !----------------------------------------------------------!
pause
cls
rem back up source file under a unique name
call ..\judge\nextback %4%1 %2 %3

echo Compiling %2.%3 ...
if "%3"=="c" goto do_c
if "%3"=="cpp" goto do_cpp
if "%3"=="pas" goto do_pas
if "%3"=="java" goto do_java
echo I don't know how to compile %2.%3: this shouldn't happen.
goto done
:do_c
rem IS THE FOLLOWING C compiler LINE RIGHT FOR YOUR SYSTEM?
rem tcc -ml %2.c
rem The following three lines work on Loyola's VC++ 6.0:
set VCPPBASE="C:\Program Files\Microsoft Visual Studio\VC98

%VCPPBASE%\Bin\cl" /I%VCPPBASE%\include" /I%VCPPBASE%\ATL\include" /nologo /MLd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /FD /GZ /c %2.%3

%VCPPBASE%\Bin\link" %2.obj /nologo /pdb:none /LIBPATH:%VCPPBASE%\lib"  /subsystem:console

goto check
:do_cpp
rem IS THE FOLLOWING C++ compiler LINE RIGHT FOR YOUR SYSTEM?
rem tcc -ml %2.cpp
rem The following three lines work on Loyola's VC++ 6.0 (same as for c)
set VCPPBASE="C:\Program Files\Microsoft Visual Studio\VC98

%VCPPBASE%\Bin\cl" /I%VCPPBASE%\include" /I%VCPPBASE%\ATL\include" /nologo /MLd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_CONSOLE" /D "_MBCS" /FD /GZ /c %2.%3

%VCPPBASE%\Bin\link" %2.obj /nologo /pdb:none /LIBPATH:%VCPPBASE%\lib"  /subsystem:console

goto check
:do_pas
rem IS THE FOLLOWING Pascal compiler LINE RIGHT FOR YOUR SYSTEM?
tpc %2.pas
goto check
:do_java
copy ..\judge\ACMIO.class
rem IS THE FOLLOWING Java compiler LINE RIGHT FOR YOUR SYSTEM?
javac %2.java
:check
echo !-----------------------------------------------------------------------!
echo ! If it didn't compile, press Ctrl-C now and report a Submission Error. !
echo !-----------------------------------------------------------------------!
pause
cls
echo Running program...
copy ..\%2\%2.in .
if "%3"=="java" goto run_java
.\%2
goto done_run

:run_java
rem IS THE FOLLOWING Java execution LINE RIGHT FOR YOUR SYSTEM?
java %2

:done_run
echo !----------------------------------------------------------------------!
echo ! Done running program...                                              !
echo ! If the program crashed or took more than a minute, press Ctrl-C now. !
echo ! Otherwise, I'll compare the team's output with the correct output.   !
echo !----------------------------------------------------------------------!
pause
cls
..\judge\diff -C2 --report-identical-files %2.out ..\%2\%2.out
if errorlevel 1 goto preschk
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !  Success on problem %1 for team %4!     !
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
goto done

:preschk
echo !---------------------------------------------------------------------!
echo ! Files did not match:                                                !
echo ! You must check for presentation errors vs wrong answer.             !     
echo ! When comparing files, press ctrl-pgdn and ctrl-pgup to swap files   !
echo !---------------------------------------------------------------------!
pause
..\judge\list %2.out ..\%2\%2.out
cls
echo !---------------------------------------------------------------------!
echo ! Was it a wrong answer or a presentation error?                      !
echo ! Record the results for problem %1 for team %4.                         !
echo !---------------------------------------------------------------------!
:done
