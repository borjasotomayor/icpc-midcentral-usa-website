@echo off
rem j.cmd - executed by judge to start grading a problem
rem     j problemLetter teamNumber
rem J.cmd only supplies names for all problems, so the main batch
rem files j1.cmd (called by j.cmd) and j2.cmd (called by j1.cmd)
rem are independent of the problem names.

if "%2z" == "z" goto usage
if not "%3z" == "z" goto usage

rem the names below change year to year 2012 VERSION
..\judge\j1 %1 %2 revenge digits slice quine markers lru juggle bounce

goto end

:usage
echo Usage: j problemLetter teamNumber
echo     starts grading a problem
echo Example:
echo     j f 12
echo grades problem f for team 12.
echo j.cmd must be executed from the team subdirectory.
echo See util.html in the notes subdirectory for more information.
:end
