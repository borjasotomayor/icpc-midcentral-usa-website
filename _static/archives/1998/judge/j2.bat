@echo off
cls
echo Found problem #%1: %2...
echo Copying source file from team diskette...
copy a:\%2.%3 c:\team
cd c:\team
echo !----------------------------------------------------------!
echo ! Done copying. Remove the team's diskette from the drive. !
echo !----------------------------------------------------------!
pause
cls
echo Compiling %2.%3 ...
if "%3"=="c" goto do_c
if "%3"=="cpp" goto do_cpp
if "%3"=="pas" goto do_pas
echo I don't know how to compile %2.%3: this shouldn't happen.
goto done
:do_c
tcc -ml %2.c
goto check
:do_cpp
tcc -ml %2.cpp
goto check
:do_pas
tpc %2.pas
:check
echo !-----------------------------------------------------------------------!
echo ! If it didn't compile, press Ctrl-C now and report a Submission Error. !
echo !-----------------------------------------------------------------------!
pause
cls
echo Running program...
copy c:\judge\%2.in c:\team
%2
echo !----------------------------------------------------------------------!
echo ! Done running program...                                              !
echo ! If the program crashed or took more than a minute, press Ctrl-C now. !
echo ! Otherwise, I'll compare the team's output with the correct output.   !
echo !----------------------------------------------------------------------!
pause
cls
c:\judge\diff -C2 --report-identical-files c:\team\%2.out c:\judge\%2.out
echo !-----------------------------------------------------------------------!
echo ! If the files are identical, press Ctrl-C now. The program is correct. !
echo ! Otherwise, you must check for presentation errors.                    !
echo !-----------------------------------------------------------------------!
pause
c:\judge\list c:\team\%2.out c:\judge\%2.out
:done
