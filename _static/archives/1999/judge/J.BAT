@echo off
rem j.bat - executed by judge to start grading a problem 
rem     j problemLetter teamNumber
rem J.bat only supplies names for all problems, so the main batch
rem files j1.bat (called by j.bat) and j2.bat (called by j1.bat)
rem are independent of the problem names.

..\judge\j1 win triangle exchange loan autoedit robot %1 %2

