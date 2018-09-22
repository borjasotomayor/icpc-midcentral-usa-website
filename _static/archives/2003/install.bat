@echo off
echo !--------------------------------------------------------------------------!
echo ! This batch file installs the judging utilities and all judge's files     !
echo ! from drive A:  into a subdirectory mcpc2003 of your current directory.   !
echo ! If you do NOT want the data to go beneath the current directory,         !
echo ! press Ctrl-C NOW.                                                        !
echo !--------------------------------------------------------------------------!
pause

xcopy a:\ mcpc2003\ /s
cd mcpc2003\team
cls
echo !------------------------------------------------------------------------!
echo ! Done.  You can remove the floppy.                                      !
echo !                                                                        !
echo ! Your site coordinator should make sure that the compile and execute    !
echo ! lines in judge\j2.bat are correct for your environment.                !
echo ! See notes\util.html if you need the details.          ! 
echo !                                                                        !
echo ! TO GRADE:  The current directory should be team.                       !
echo ! You grade at the DOS prompt in the team directory by entering          !
echo !    j problemLetter teamNumber                                          !
echo ! For instance to grade problem f for team 5 enter                       !
echo !    j f 5         (or j F 5)                                            !
echo ! This will grade and keep an archival copy of the submission.  If the   !
echo ! example above is team 5's 9th submission of a C++ solution for problem !
echo ! f that is graded on this computer, it is archived as back\5f9.cpp      !
echo !                                                                        !
echo ! Open a web browser on mcpc2003\browse.html and you will have links to  !
echo ! all the documents for this competition.  In particular see the link to !
echo !    notes\util.html       for more information on the utilities.        ! 
echo ! plus you can browse all the problems, solutions, test data,            !
echo ! and other administrative information.                                  !
echo !------------------------------------------------------------------------!
