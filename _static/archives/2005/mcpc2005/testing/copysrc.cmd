@echo off
if exist %1\%1.java goto java
if exist %1\%1.cpp goto cpp
if exist %1\%1.c goto c 
echo no source in %1
goto end

:java
copy %1\%1.java a:\
goto end

:cpp
copy %1\%1.cpp a:\
goto end

:c
copy %1\%1.c a:\

:end
