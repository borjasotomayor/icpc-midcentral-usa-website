@echo off
echo !---------------------------------------------------------------------------!
echo ! This batch file installs the judging utilities                            !
echo ! from drive A:  into a subdirectory ACM99 of your current directory.       !
echo ! If beneath the current directory is NOT where you want the data to go,    !
echo ! press Ctrl-C NOW.                                                         !
echo !---------------------------------------------------------------------------!
pause

md ACM99
cd ACM99
md judge
md team
md back
copy a:\judge\*.* judge
copy a:\win\win.in judge
copy a:\win\win.out judge
copy a:\triangle\triangle.in judge
copy a:\triangle\triangle.out judge
copy a:\exchange\exchange.in judge
copy a:\exchange\exchange.out judge
copy a:\loan\loan.in judge
copy a:\loan\loan.out judge
copy a:\autoedit\autoedit.in judge
copy a:\autoedit\autoedit.out judge
copy a:\robot\robot.in judge
copy a:\robot\robot.out judge
copy judge\j.bat team
echo root dir marker -- do not delete! > rootmark.txt
cd team
cls
echo !------------------------------------------------------------------------!
echo ! Done.  You can remove the floppy.                                      !
echo !                                                                        !
echo ! The current directory should be team.                                  !
echo ! You grade at the DOS prompt in the team directory by entering          !
echo !    j problemLetter teamNumber                                          !
echo ! For instance to grade problem d for team 5 enter                       !
echo !    j d 5         (or j D 5)                                            !
echo ! This will grade and keep an archival copy of the submission.           !
echo ! If the example above is team 5's 12th submission of loan.cpp for       !
echo ! problem d saved on this computer, it is archived as back\5d12.cpp      !
echo !                                                                        !
echo ! For more information see util.html in the notes directory of the       ! 
echo ! floppy disk, or see the text of the html page in read.me in the        !
echo ! installed judge directory.                                             !
echo !------------------------------------------------------------------------!
