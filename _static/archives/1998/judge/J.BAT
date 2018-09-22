@echo off
if "%1"=="" goto oops
c:
cd \
cls
echo !---------------------------------------------------!
echo ! Make sure that the team's diskette is in drive A: !
echo !---------------------------------------------------!
pause
echo Deleting last submission...
echo y | del c:\team\*.*
if "%1"=="1" goto booklet
if "%1"=="2" goto rect
if "%1"=="3" goto rook
if "%1"=="4" goto self
if "%1"=="5" goto untwist
if "%1"=="6" goto word
cls
echo !----------------------------!
echo ! There is no problem %1...
echo !----------------------------!
goto done
:oops
cls
echo !-----------------------------------------------!
echo ! You must provide a problem number. Try again. !
echo !-----------------------------------------------!
goto done
:booklet
if exist a:booklet.c c:\judge\j2 1 booklet c
if exist a:booklet.cpp c:\judge\j2 1 booklet cpp
if exist a:booklet.pas c:\judge\j2 1 booklet pas
goto nofile
:rect
if exist a:rect.c c:\judge\j2 2 rect c
if exist a:rect.cpp c:\judge\j2 2 rect cpp
if exist a:rect.pas c:\judge\j2 2 rect pas
goto nofile
:rook
if exist a:rook.c c:\judge\j2 3 rook c
if exist a:rook.cpp c:\judge\j2 3 rook cpp
if exist a:rook.pas c:\judge\j2 3 rook pas
goto nofile
:self
if exist a:self.c c:\judge\j2 4 self c
if exist a:self.cpp c:\judge\j2 4 self cpp
if exist a:self.pas c:\judge\j2 4 self pas
goto nofile
:untwist
if exist a:untwist.c c:\judge\j2 5 untwist c
if exist a:untwist.cpp c:\judge\j2 5 untwist cpp
if exist a:untwist.pas c:\judge\j2 5 untwist pas
goto nofile
:word
if exist a:word.c c:\judge\j2 6 word c
if exist a:word.cpp c:\judge\j2 6 word cpp
if exist a:word.pas c:\judge\j2 6 word pas
:nofile
cls
echo !-----------------------------------------------------------------------!
echo ! I can't find a source file for problem %1.  Report a Submission Error. !
echo !-----------------------------------------------------------------------!
:done
