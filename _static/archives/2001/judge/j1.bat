@echo off
rem j1.bat - executed from j.bat 
rem locates named problem, initializes team directory
rem     j1 name1 name2 name3 name4 name5 name6 name7 problemLetter teamNumber

rem shift
if "%9z" == "z" goto oops
rem if not "%9z"=="z" goto oops
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
if "%9"=="1" goto isnum
if "%9"=="2" goto isnum
if "%9"=="3" goto isnum
if "%9"=="4" goto isnum
if "%9"=="5" goto isnum
if "%9"=="6" goto isnum
if "%9"=="7" goto isnum
if "%9"=="8" goto isnum
if "%9"=="9" goto isnum
if "%9"=="10" goto isnum
if "%9"=="11" goto isnum
if "%9"=="12" goto isnum
if "%9"=="13" goto isnum
if "%9"=="14" goto isnum
if "%9"=="10" goto isnum
if "%9"=="15" goto isnum
if "%9"=="16" goto isnum
if "%9"=="17" goto isnum
if "%9"=="18" goto isnum
if "%9"=="19" goto isnum
if "%9"=="20" goto isnum
if "%9"=="21" goto isnum
if "%9"=="22" goto isnum
if "%9"=="23" goto isnum
if "%9"=="24" goto isnum
if "%9"=="20" goto isnum
if "%9"=="25" goto isnum
if "%9"=="26" goto isnum
if "%9"=="27" goto isnum
if "%9"=="28" goto isnum
if "%9"=="29" goto isnum
if "%9"=="30" goto isnum
goto oops

:isnum
if "%8"=="a" goto a
if "%8"=="A" goto a
if "%8"=="b" goto b
if "%8"=="B" goto b
if "%8"=="c" goto c
if "%8"=="C" goto c
if "%8"=="d" goto d
if "%8"=="D" goto d
if "%8"=="e" goto e
if "%8"=="E" goto e
if "%8"=="f" goto f
if "%8"=="F" goto f
if "%8"=="g" goto g
if "%8"=="G" goto g
cls
echo !----------------------------------------------------------!
echo ! There is no problem %8 -- SHOULD BE a, b, c, d, e, f OR g !
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
if exist a:%1.c ..\judge\j2 a %1 c %9
if exist a:%1.cpp ..\judge\j2 a %1 cpp %9
if exist a:%1.pas ..\judge\j2 a %1 pas %9
if exist a:%1.java ..\judge\j2 a %1 java %9
goto nofile
:b
if exist a:%2.c ..\judge\j2 b %2 c %9
if exist a:%2.cpp ..\judge\j2 b %2 cpp %9
if exist a:%2.pas ..\judge\j2 b %2 pas %9
if exist a:%2.java ..\judge\j2 b %2 java %9
goto nofile
:c
if exist a:%3.c ..\judge\j2 c %3 c %9
if exist a:%3.cpp ..\judge\j2 c %3 cpp %9
if exist a:%3.pas ..\judge\j2 c %3 pas %9
if exist a:%3.java ..\judge\j2 c %3 java %9
goto nofile
:d
if exist a:%4.c ..\judge\j2 d %4 c %9
if exist a:%4.cpp ..\judge\j2 d %4 cpp %9
if exist a:%4.pas ..\judge\j2 d %4 pas %9
if exist a:%4.java ..\judge\j2 d %4 java %9
goto nofile
:e
if exist a:%5.c ..\judge\j2 e %5 c %9
if exist a:%5.cpp ..\judge\j2 e %5 cpp %9
if exist a:%5.pas ..\judge\j2 e %5 pas %9
if exist a:%5.java ..\judge\j2 e %5 java %9
goto nofile
:f
if exist a:%6.c ..\judge\j2 f %6 c %9
if exist a:%6.cpp ..\judge\j2 f %6 cpp %9
if exist a:%6.pas ..\judge\j2 f %6 pas %9
if exist a:%6.java ..\judge\j2 f %6 java %9
:g
if exist a:%7.c ..\judge\j2 g %7 c %9
if exist a:%7.cpp ..\judge\j2 g %7 cpp %9
if exist a:%7.pas ..\judge\j2 g %7 pas %9
if exist a:%7.java ..\judge\j2 g %7 java %9
:nofile
cls
echo !-----------------------------------------------------------------------!
echo ! I can't find a source file for problem %8.  Report a Submission Error. !
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
