@echo off
echo This installs the judging utilities on drive C:.
echo The judges' diskette must be in drive A:.
pause
md c:\team
md c:\judge
copy a:\judge\*.* c:\judge
copy a:\booklet\booklet.in c:\judge
copy a:\booklet\booklet.out c:\judge
copy a:\rect\rect.in c:\judge
copy a:\rect\rect.out c:\judge
copy a:\rook\rook.in c:\judge
copy a:\rook\rook.out c:\judge
rem copy a:\self\self.in c:\judge
copy a:\self\self.out c:\judge
copy a:\untwist\untwist.in c:\judge
copy a:\untwist\untwist.out c:\judge
copy a:\word\word.in c:\judge
copy a:\word\word.out c:\judge
copy c:\judge\j.bat c:\
c:
cd \
cls
echo Done.  You can remove the floppy.
