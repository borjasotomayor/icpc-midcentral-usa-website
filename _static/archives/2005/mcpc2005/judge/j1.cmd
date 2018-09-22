@echo off
rem j1.cmd - executed from j.cmd 
rem locates named problem, initializes team directory
rem  j1 problemLetter teamNumber problemAFileBase problemAFileBase  ... 

rem Modify the drive letter below !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
rem     so it matches the source drive for team programs !!!!!!!!!!!!!
set MCPCdrive=a:


rem make sure in right directory!
if not exist ..\rootmark.txt goto norootmk
cd ..\team
if not exist j.cmd goto expectj
if exist j2.cmd goto noj2

set MCPCletter=%1
set MCPCteam=%2
if "%MCPCteam%"=="1" goto isnum
if "%MCPCteam%"=="2" goto isnum
if "%MCPCteam%"=="3" goto isnum
if "%MCPCteam%"=="4" goto isnum
if "%MCPCteam%"=="5" goto isnum
if "%MCPCteam%"=="6" goto isnum
if "%MCPCteam%"=="7" goto isnum
if "%MCPCteam%"=="8" goto isnum
if "%MCPCteam%"=="9" goto isnum
if "%MCPCteam%"=="10" goto isnum
if "%MCPCteam%"=="11" goto isnum
if "%MCPCteam%"=="12" goto isnum
if "%MCPCteam%"=="13" goto isnum
if "%MCPCteam%"=="14" goto isnum
if "%MCPCteam%"=="10" goto isnum
if "%MCPCteam%"=="15" goto isnum
if "%MCPCteam%"=="16" goto isnum
if "%MCPCteam%"=="17" goto isnum
if "%MCPCteam%"=="18" goto isnum
if "%MCPCteam%"=="19" goto isnum
if "%MCPCteam%"=="20" goto isnum
if "%MCPCteam%"=="21" goto isnum
if "%MCPCteam%"=="22" goto isnum
if "%MCPCteam%"=="23" goto isnum
if "%MCPCteam%"=="24" goto isnum
if "%MCPCteam%"=="20" goto isnum
if "%MCPCteam%"=="25" goto isnum
if "%MCPCteam%"=="26" goto isnum
if "%MCPCteam%"=="27" goto isnum
if "%MCPCteam%"=="28" goto isnum
if "%MCPCteam%"=="29" goto isnum
if "%MCPCteam%"=="30" goto isnum
goto oops

:isnum
if "%MCPCletter%"=="a" goto a
if "%MCPCletter%"=="A" goto a
if "%MCPCletter%"=="b" goto b
if "%MCPCletter%"=="B" goto b
if "%MCPCletter%"=="c" goto c
if "%MCPCletter%"=="C" goto c
if "%MCPCletter%"=="d" goto d
if "%MCPCletter%"=="D" goto d
if "%MCPCletter%"=="e" goto e
if "%MCPCletter%"=="E" goto e
if "%MCPCletter%"=="f" goto f
if "%MCPCletter%"=="F" goto f
if "%MCPCletter%"=="g" goto g
if "%MCPCletter%"=="G" goto g
if "%MCPCletter%"=="h" goto h
if "%MCPCletter%"=="H" goto h
if "%MCPCletter%"=="i" goto i
if "%MCPCletter%"=="I" goto i
if "%MCPCletter%"=="j" goto j
if "%MCPCletter%"=="J" goto j
goto badLetter

:j
shift
:i
shift
:h
shift
:g
shift
:f
shift
:e
shift
:d
shift
:c
shift
:b
shift
rem Now the proper problem name is shifted to the space of the first problem. 

:a

if "%3z"=="z" goto highLetter

cls
echo !---------------------------------------------------!
echo ! Make sure that the team's source is in drive %MCPCdrive%: !
echo !---------------------------------------------------!
pause
if not exist %MCPCdrive%\ goto setSrcDrive

echo Deleting last submission...
echo y | del *.*
rem replace batch file deleted from team directory
copy ..\judge\j.cmd .


set MCPClang=None
if exist %MCPCdrive%\%3.c    set MCPClang=c
if exist %MCPCdrive%\%3.cpp  set MCPClang=cpp
if exist %MCPCdrive%\%3.java set MCPClang=java

if not %MCPClang%==None ..\judge\j2 %MCPCletter% %3 %MCPClang% %MCPCteam% %MCPCdrive% 

rem The script terminates if the source file is found and j2 is invoked.

cls
echo !------------------------------------------------------------------------!
echo ! I can't find a source file for the problem:  Report a Submission Error.!
echo !------------------------------------------------------------------------!
goto done


rem ------------------------------------------------------------------------ 
rem  Only error exits for bad batch file parameters and setup follow 

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

:setSrcDrive
echo !----------------------------------------------------------!
echo ! Can not find drive %MCPCdrive%                                    !
echo ! It is set in judge\j1.cmd near the top.                  !
echo ! Modify the script so the right value is set.             !
echo ! For instance if input is on a flash drive in e:, make it !
echo !   set MCPCdrive=e:                                       !
echo !----------------------------------------------------------!
goto done

:badLetter
cls
echo !----------------------------------------------------------------!
echo ! There is no problem %MCPCletter%: IT MIGHT BE a,b,c,d,e,f,g,h,i OR j! 
echo !----------------------------------------------------------------!
goto done

:highLetter
echo !---------------------------------------------------------!
echo ! The problem letter you gave is too high for this year.  !
echo !---------------------------------------------------------!
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
