@echo off
rem j1.bat - executed from j.bat 
rem locates named problem, initializes team directory
rem     j1 name1 name2 name3 name4 name5 name6 problemLetter teamNumber


if "%8z" == "z" goto oops
if not "%9z"=="z" goto oops
rem make sure in right directory!
if not exist ..\rootmark.txt goto norootmk
cd ..\team
if not exist j.bat goto expectj
if exist j2.bat goto noj2
cls
echo !---------------------------------------------------!
echo ! Make sure that the team's diskette is in drive A: !
echo !---------------------------------------------------!
pause
echo Deleting last submission...
echo y | del *.*
rem replace batch file deleted from team directory
copy ..\judge\j.bat .
if "%8"=="1" goto isnum
if "%8"=="2" goto isnum
if "%8"=="3" goto isnum
if "%8"=="4" goto isnum
if "%8"=="5" goto isnum
if "%8"=="6" goto isnum
if "%8"=="7" goto isnum
if "%8"=="8" goto isnum
if "%8"=="9" goto isnum
if "%8"=="10" goto isnum
if "%8"=="11" goto isnum
if "%8"=="12" goto isnum
if "%8"=="13" goto isnum
if "%8"=="14" goto isnum
if "%8"=="10" goto isnum
if "%8"=="15" goto isnum
if "%8"=="16" goto isnum
if "%8"=="17" goto isnum
if "%8"=="18" goto isnum
if "%8"=="19" goto isnum
if "%8"=="20" goto isnum
if "%8"=="21" goto isnum
if "%8"=="22" goto isnum
if "%8"=="23" goto isnum
if "%8"=="24" goto isnum
if "%8"=="20" goto isnum
if "%8"=="25" goto isnum
if "%8"=="26" goto isnum
if "%8"=="27" goto isnum
if "%8"=="28" goto isnum
if "%8"=="29" goto isnum
if "%8"=="30" goto isnum
goto oops

:isnum
if "%7"=="a" goto a
if "%7"=="A" goto a
if "%7"=="b" goto b
if "%7"=="B" goto b
if "%7"=="c" goto c
if "%7"=="C" goto c
if "%7"=="d" goto d
if "%7"=="D" goto d
if "%7"=="e" goto e
if "%7"=="E" goto e
if "%7"=="f" goto f
if "%7"=="F" goto f
cls
echo !----------------------------------------------------------!
echo ! There is no problem %7  -- SHOULD BE a, b, c, d, e OR f   
echo !----------------------------------------------------------!
goto done
:oops
cls
echo !----------------------------------------------------------!
echo ! You must provide                                         !
echo !   a problem letter and team number no more than 30.      !
echo ! For example to grade problem e by team 12 execute:       !
echo !    j e 12                                                !
echo ! Try again                                                !
echo !----------------------------------------------------------!
goto done
:a
if exist a:%1.c ..\judge\j2 a %1 c %8
if exist a:%1.cpp ..\judge\j2 a %1 cpp %8
if exist a:%1.pas ..\judge\j2 a %1 pas %8
if exist a:%1.java ..\judge\j2 a %1 java %8
goto nofile
:b
if exist a:%2.c ..\judge\j2 b %2 c %8
if exist a:%2.cpp ..\judge\j2 b %2 cpp %8
if exist a:%2.pas ..\judge\j2 b %2 pas %8
if exist a:%2.java ..\judge\j2 b %2 java %8
goto nofile
:c
if exist a:%3.c ..\judge\j2 c %3 c %8
if exist a:%3.cpp ..\judge\j2 c %3 cpp %8
if exist a:%3.pas ..\judge\j2 c %3 pas %8
if exist a:%3.java ..\judge\j2 c %3 java %8
goto nofile
:d
if exist a:%4.c ..\judge\j2 d %4 c %8
if exist a:%4.cpp ..\judge\j2 d %4 cpp %8
if exist a:%4.pas ..\judge\j2 d %4 pas %8
if exist a:%4.java ..\judge\j2 d %4 java %8
goto nofile
:e
if exist a:%5.c ..\judge\j2 e %5 c %8
if exist a:%5.cpp ..\judge\j2 e %5 cpp %8
if exist a:%5.pas ..\judge\j2 e %5 pas %8
if exist a:%5.java ..\judge\j2 e %5 java %8
goto nofile
:f
if exist a:%6.c ..\judge\j2 f %6 c %8
if exist a:%6.cpp ..\judge\j2 f %6 cpp %8
if exist a:%6.pas ..\judge\j2 f %6 pas %8
if exist a:%6.java ..\judge\j2 f %6 java %8
:nofile
cls
echo !-----------------------------------------------------------------------!
echo ! I can't find a source file for problem %7.  Report a Submission Error. !
echo !-----------------------------------------------------------------------!
goto done

:norootmk
echo Expect some file named rootmark.txt in parent directory of team directory
goto wrongdir
:expectj
echo Expect j.bat in the team directory.
echo If it is missing, copy it from the judge directory.
goto wrongdir
:noj2
echo Are you in the judge directory by mistake?
echo j2.bat belongs in the judge directory, not the team directory.
:wrongdir
echo The current directory should be   team 
echo   when running the judging utility.
echo Change your directory to the team directory and run j again.
:done
