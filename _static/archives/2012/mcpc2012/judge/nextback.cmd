@echo off
rem nextback savePrefix sourceName sourceExt
rem copies sourceName.sourceExt to 
rem     ..\back\savePrefix#.sourceExt
rem   for first number # so the file does not already exist
rem batch file is called by j2.cmd

if exist ..\back\%11.%3 goto n2
copy %2.%3 ..\back\%11.%3
echo Backed up %2.%3 to ..\back\%11.%3
goto done
:n2
if exist ..\back\%12.%3 goto n3
copy %2.%3 ..\back\%12.%3
echo Backed up %2.%3 to ..\back\%12.%3
goto done
:n3
if exist ..\back\%13.%3 goto n4
copy %2.%3 ..\back\%13.%3
echo Backed up %2.%3 to ..\back\%13.%3
goto done
:n4
if exist ..\back\%14.%3 goto n5
copy %2.%3 ..\back\%14.%3
echo Backed up %2.%3 to ..\back\%14.%3
goto done
:n5
if exist ..\back\%15.%3 goto n6
copy %2.%3 ..\back\%15.%3
echo Backed up %2.%3 to ..\back\%15.%3
goto done
:n6
if exist ..\back\%16.%3 goto n7
copy %2.%3 ..\back\%16.%3
echo Backed up %2.%3 to ..\back\%16.%3
goto done
:n7
if exist ..\back\%17.%3 goto n8
copy %2.%3 ..\back\%17.%3
echo Backed up %2.%3 to ..\back\%17.%3
goto done
:n8
if exist ..\back\%18.%3 goto n9
copy %2.%3 ..\back\%18.%3
echo Backed up %2.%3 to ..\back\%18.%3
goto done
:n9
if exist ..\back\%19.%3 goto n10
copy %2.%3 ..\back\%19.%3
echo Backed up %2.%3 to ..\back\%19.%3
goto done
:n10
if exist ..\back\%110.%3 goto n11
copy %2.%3 ..\back\%110.%3
echo Backed up %2.%3 to ..\back\%110.%3
goto done
:n11
if exist ..\back\%111.%3 goto n12
copy %2.%3 ..\back\%111.%3
echo Backed up %2.%3 to ..\back\%111.%3
goto done
:n12
if exist ..\back\%112.%3 goto n13
copy %2.%3 ..\back\%112.%3
echo Backed up %2.%3 to ..\back\%112.%3
goto done
:n13
if exist ..\back\%113.%3 goto n14
copy %2.%3 ..\back\%113.%3
echo Backed up %2.%3 to ..\back\%113.%3
goto done
:n14
if exist ..\back\%114.%3 goto n15
copy %2.%3 ..\back\%114.%3
echo Backed up %2.%3 to ..\back\%114.%3
goto done
:n15
if exist ..\back\%115.%3 goto n16
copy %2.%3 ..\back\%115.%3
echo Backed up %2.%3 to ..\back\%115.%3
goto done
:n16
if exist ..\back\%116.%3 goto n17
copy %2.%3 ..\back\%116.%3
echo Backed up %2.%3 to ..\back\%116.%3
goto done
:n17
if exist ..\back\%117.%3 goto n18
copy %2.%3 ..\back\%117.%3
echo Backed up %2.%3 to ..\back\%117.%3
goto done
:n18
if exist ..\back\%118.%3 goto n19
copy %2.%3 ..\back\%118.%3
echo Backed up %2.%3 to ..\back\%118.%3
goto done
:n19
if exist ..\back\%119.%3 goto n20
copy %2.%3 ..\back\%119.%3
echo Backed up %2.%3 to ..\back\%119.%3
goto done
:n20
if exist ..\back\%120.%3 goto giveup
copy %2.%3 ..\back\%120.%3
echo Backed up %2.%3 to ..\back\%120.%3
goto done
:giveup
echo Gave up on backups after 20 previous ones!
:done
