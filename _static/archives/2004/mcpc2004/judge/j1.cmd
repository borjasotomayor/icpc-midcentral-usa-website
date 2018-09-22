@echo off
rem j1.cmd - executed from j.cmd 
rem locates named problem, initializes team directory
rem  j1 problemLetter teamNumber name1 name2 name3 name4 name5 name6 name7 name8
rem after shift  0        1        2     3                 8 problems some years
shift
rem digit below should be one more than the number of problems
rem make sure in right directory!
if not exist ..\rootmark.txt goto norootmk
cd ..\team
if not exist j.cmd goto expectj
if exist j2.cmd goto noj2
cls
echo !---------------------------------------------------!
echo ! Make sure that the team's diskette is in drive A: !
echo !---------------------------------------------------!
pause
echo Deleting last submission...
echo y | del *.*
rem replace batch file deleted from team directory
copy ..\judge\j.cmd .
if "%1"=="1" goto isnum
if "%1"=="2" goto isnum
if "%1"=="3" goto isnum
if "%1"=="4" goto isnum
if "%1"=="5" goto isnum
if "%1"=="6" goto isnum
if "%1"=="7" goto isnum
if "%1"=="8" goto isnum
if "%1"=="9" goto isnum
if "%1"=="10" goto isnum
if "%1"=="11" goto isnum
if "%1"=="12" goto isnum
if "%1"=="13" goto isnum
if "%1"=="14" goto isnum
if "%1"=="10" goto isnum
if "%1"=="15" goto isnum
if "%1"=="16" goto isnum
if "%1"=="17" goto isnum
if "%1"=="18" goto isnum
if "%1"=="19" goto isnum
if "%1"=="20" goto isnum
if "%1"=="21" goto isnum
if "%1"=="22" goto isnum
if "%1"=="23" goto isnum
if "%1"=="24" goto isnum
if "%1"=="20" goto isnum
if "%1"=="25" goto isnum
if "%1"=="26" goto isnum
if "%1"=="27" goto isnum
if "%1"=="28" goto isnum
if "%1"=="29" goto isnum
if "%1"=="30" goto isnum
goto oops

:isnum
if "%0"=="a" goto a
if "%0"=="A" goto a
if "%0"=="b" goto b
if "%0"=="B" goto b
if "%0"=="c" goto c
if "%0"=="C" goto c
if "%0"=="d" goto d
if "%0"=="D" goto d
if "%0"=="e" goto e
if "%0"=="E" goto e
if "%0"=="f" goto f
if "%0"=="F" goto f
if "%0"=="g" goto g
if "%0"=="G" goto g
rem if "%0"=="h" goto h
rem if "%0"=="H" goto h
cls
echo !----------------------------------------------------------!
rem echo ! There is no problem %0: SHOULD BE a,b,c,d,e,f,g OR h!
echo ! There is no problem %0: SHOULD BE a,b,c,d,e,f OR g!
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
if exist a:\%2.c ..\judge\j2 a %2 c %1
if exist a:\%2.cpp ..\judge\j2 a %2 cpp %1
if exist a:\%2.java ..\judge\j2 a %2 java %1
goto nofile
:b
if exist a:\%3.c ..\judge\j2 b %3 c %1
if exist a:\%3.cpp ..\judge\j2 b %3 cpp %1
if exist a:\%3.java ..\judge\j2 b %3 java %1
goto nofile
:c
if exist a:\%4.c ..\judge\j2 c %4 c %1
if exist a:\%4.cpp ..\judge\j2 c %4 cpp %1
if exist a:\%4.java ..\judge\j2 c %4 java %1
goto nofile
:d
if exist a:\%5.c ..\judge\j2 d %5 c %1
if exist a:\%5.cpp ..\judge\j2 d %5 cpp %1
if exist a:\%5.java ..\judge\j2 d %5 java %1
goto nofile
:e
if exist a:\%6.c ..\judge\j2 e %6 c %1
if exist a:\%6.cpp ..\judge\j2 e %6 cpp %1
if exist a:\%6.java ..\judge\j2 e %6 java %1
goto nofile
:f
if exist a:\%7.c ..\judge\j2 f %7 c %1
if exist a:\%7.cpp ..\judge\j2 f %7 cpp %1
if exist a:\%7.java ..\judge\j2 f %7 java %1
:g
if exist a:\%8.c ..\judge\j2 g %8 c %1
if exist a:\%8.cpp ..\judge\j2 g %8 cpp %1
if exist a:\%8.java ..\judge\j2 g %8 java %1
:h
if exist a:\%9.c ..\judge\j2 h %9 c %1
if exist a:\%9.cpp ..\judge\j2 h %9 cpp %1
if exist a:\%9.java ..\judge\j2 h %9 java %1
:nofile
cls
echo !-----------------------------------------------------------------------!
echo ! I can't find a source file for problem %0  Report a Submission Error. !
echo !-----------------------------------------------------------------------!
goto done

:norootmk
echo Expect some file named rootmark.txt in parent directory of team directory
goto wrongdir
:expectj
echo Expect j.cmd in the team directory.
echo If it is missing, copy it from the judge directory.
goto wrongdir
:noj2
echo Are you in the judge directory by mistake?
echo j2.cmd belongs in the judge directory, not the team directory.
:wrongdir
echo The current directory should be   team 
echo   when running the judging utility.
echo Change your directory to the team directory and run j again.
:done
