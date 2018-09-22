@echo off
rem j2 problemLetter problemName sourceExt team# srcDrive 
rem called from j1.cmd after source file found
rem 2005 version with srcDrive and problem output to stdout

cls
echo Found lettered problem %1: %2.%3 of team %4 on drive %5
echo Copying source file from team diskette...
copy %5\%2.%3 .
echo !----------------------------------------------------------!
echo ! Done copying. Remove the team's media from the drive.    !
echo !----------------------------------------------------------!
pause
cls
rem back up source file under a unique name
call ..\judge\nextback %4%1 %2 %3

echo Compiling %2.%3 ...
if "%3"=="c" goto do_c
if "%3"=="cpp" goto do_cpp
if "%3"=="java" goto do_java
echo I don't know how to compile %2.%3: this shouldn't happen.
goto done

:do_c
gcc -o %2.exe %2.c
goto check

:do_cpp
g++ -o %2.exe %2.cpp
goto check

:do_java
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
rem new for 2005:  standard output redirected
.\%2 > %2.out
goto done_run

:run_java
rem new for 2005:  standard output redirected
java %2 > %2.out

:done_run
echo !----------------------------------------------------------------------!
echo ! Done running program...                                              !
echo ! If the program crashed or took more than your site's time limit,     !
echo ! press Ctrl-C now.                                                    !
echo ! Otherwise, I'll compare the team's output with the correct output.   !
echo !----------------------------------------------------------------------!
pause
cls
fc %2.out ..\%2\%2.out
if errorlevel 1 goto preschk
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !  Success on problem %1 for team %4!     !
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
goto done

:preschk
echo !---------------------------------------------------------------------!
echo ! Files did not match:                                                !
echo ! You must check for presentation errors vs wrong answer.             !     
echo ! See the popup file comparison window.                               !
echo !                                                                     !
echo ! Was it a wrong answer or a presentation error?                      !
echo ! Record the results for problem %1 for team %4.                        !
echo !---------------------------------------------------------------------!
..\judge\KDiff3 %2.out ..\%2\%2.out
:done
