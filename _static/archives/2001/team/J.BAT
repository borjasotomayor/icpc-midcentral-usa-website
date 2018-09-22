@echo off
rem j.bat - executed by judge to start grading a problem 
rem     j problemLetter teamNumber
rem J.bat only supplies names for all problems, so the main batch
rem files j1.bat (called by j.bat) and j2.bat (called by j1.bat)
rem are independent of the problem names.

if "%2z" == "z" goto usage
if not "%3z" == "z" goto usage

rem the names below change year to year
..\judge\j1 transmit wscipher split indexgen hangover double image %1 %2


goto end

:usage
echo Usage: j problemLetter teamNumber
echo     starts grading a problem
echo Example:
echo     j f 12
echo grades problem f for team 12. 
echo j.bat must be executed from the team subdirectory.
echo See util.html in the notes subdirectory for more information. 
:end
