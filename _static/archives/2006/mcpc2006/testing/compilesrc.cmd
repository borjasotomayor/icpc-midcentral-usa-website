@echo off
cd %1
if exist %1.java javac %1.java
if exist %1.cpp g++ -o %1.exe %1.cpp
if exist %1.c gcc -o %1.exe %1.c
cd ..