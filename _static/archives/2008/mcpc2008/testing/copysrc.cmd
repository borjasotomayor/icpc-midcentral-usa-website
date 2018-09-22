@echo off
set MCPCdrive=a:
if  "z%1" == "z" goto usage
if not "z%2" == "z" set MCPCdrive=%2
if exist %1\%1.java goto java
if exist %1\%1.cpp goto cpp
if exist %1\%1.c goto c 
echo no source in %1
goto end

:usage
echo Usage:
echo   copysrc probName [destDir]
echo destDir defaults to a:  
goto end

:java
copy %1\%1.java %MCPCdrive%\
goto end

:cpp
copy %1\%1.cpp %MCPCdrive%\
goto end

:c
copy %1\%1.c %MCPCdrive%\

:end
