@echo off
echo !--------------------------------------------------------------------------!
echo ! This script installs the judging utilities and all judge's files         !
echo ! into a subdirectory mcpc2012 of your current directory.                  !
echo ! If you do NOT want the data to go beneath the current directory,         !
echo ! press Ctrl-C NOW.                                                        !
echo !--------------------------------------------------------------------------!
pause

if "%1z" == "z" goto tryE
if not exist %1\mcpc2012\rootmark.txt goto badParam
xcopy %1\mcpc2012 mcpc2012\ /s
goto finish

:badParam
echo !--------------------------------------------------------------------------!
echo ! The parameter you supplied                                               !
echo !   %1
echo ! was not a directory containing subdirectory mcpc2012.                    !
echo ! Either try again with a correct directory parameter, or give no          !
echo ! parameter, and the script will search on drives E:, D:, C:, F:, and G:.  !
echo !--------------------------------------------------------------------------!
goto theEnd

:tryE
if not exist e:\mcpc2012\rootmark.txt goto tryD
xcopy e:\mcpc2012 mcpc2012\ /s
goto finish

:tryD
echo Not at E:\mcpc2012, trying D:
if not exist d:\mcpc2012\rootmark.txt goto tryC
xcopy d:\mcpc2012 mcpc2012\ /s
goto finish

:tryC
echo Not at D:\mcpc2012, trying C:
if not exist c:\mcpc2012\rootmark.txt goto tryF
xcopy c:\mcpc2012 mcpc2012\ /s
goto finish

:tryF
echo Not at C:\mcpc2012, trying F:
if not exist f:\mcpc2012\rootmark.txt goto tryG
xcopy f:\mcpc2012 mcpc2012\ /s
goto finish

:tryG
echo Not at F:\mcpc2012, trying G:
if not exist g:\mcpc2012\rootmark.txt goto failed
xcopy g:\mcpc2012 mcpc2012\ /s
goto finish

:failed
echo !--------------------------------------------------------------------------!
echo ! The proper source was not found in drive C:, D:, E:, F: or G:.           !
echo ! If you can correct that, do so and try again.                            !
echo ! Otherwise run the install script again with a parameter giving the       !
echo ! directory which DOES contain mcpc2012.                                   !
echo !--------------------------------------------------------------------------!
goto theEnd

:finish
cd mcpc2012\team
cls
echo !------------------------------------------------------------------------!
echo ! Done.  You can remove the CD.                                          !
echo !                                                                        !
echo ! Your site director should make sure that the team source drive is set  !
echo ! correctly for your environment near the top of judge\j1.cmd            !
echo ! See notes\util.html if you need the details.                           !
echo !                                                                        !
echo ! TO GRADE:  The current directory should be team.                       !
echo ! You grade at the Command Prompt in the team directory by entering      !
echo !    j problemLetter teamNumber                                          !
echo ! For instance to grade problem f for team 5 enter                       !
echo !    j f 5         (or j F 5)                                            !
echo ! This will grade and keep an archival copy of the submission.  If the   !
echo ! example above is team 5's 9th submission of a C++ solution for problem !
echo ! f that is graded on this computer, it is archived as back\5f9.cpp      !
echo !                                                                        !
echo ! Open a web browser on mcpc2012\browse.html and you will have links to  !
echo ! all the documents for this competition.  In particular see the link to !
echo !    notes\util.html       for more information on the utilities.        !
echo ! Also you can browse all the problems, solutions, test data, judge's    !
echo ! notes, and other administrative information.                           !
echo !------------------------------------------------------------------------!
:theEnd
