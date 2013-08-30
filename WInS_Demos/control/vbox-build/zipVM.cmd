@echo off

set overall_start_time=%time:~0,2%%time:~3,2%
set dt=%date:~-4%%date:~4,2%%date:~7,2%_%time:~0,2%%time:~3,2%

set DIR_NAME=%1
set OVA_FILENAME=%2
set ZIP_OUTPUT_DIR=%3

echo Zipping OVA=[%OVA_FILENAME%] into output dir=[%ZIP_OUTPUT_DIR%]

rem set DirPath=%0

rem :loop1
rem   If "%DirPath%" == "" GoTo :done
rem   For /F "tokens=1* delims=\" %%a in ("%DirPath%") Do set memory=%%a
rem   For /F "tokens=1* delims=\" %%a in ("%DirPath%") Do Set DirPath=%%b
  rem GoTo :loop1

rem :done
  rem echo %memory%
  rem goto :end

rem echo DirPath=%DirPath%

set ZIP_CLI_CMD=c:\progra~1\7-Zip\7z.exe
set ZIP_METHOD=zip
SET ZIP_MAX_SIZE=2g
SET ZIP_DEST_FILE=%ZIP_OUTPUT_DIR%\%OVA_FILENAME%.zip

set zip_start_time=%time:~0,2%%time:~3,2%
echo zip start time=%zip_start_time%

set ZIP_CLI_OPTS=a -tzip -mx=0 -md=512m -mmt=4 -mfb=258 -mpass=15 -v%ZIP_MAX_SIZE%
echo %ZIP_CLI_OPTS%

set CMDLINE=%ZIP_CLI_CMD% %ZIP_CLI_OPTS% %ZIP_DEST_FILE% %DIR_NAME%\%OVA_FILENAME% %DIR_NAME%\%OVA_FILENAME%.md5
echo %CMDLINE%

%CMDLINE%

set zip_stop_time=%time:~0,2%%time:~3,2%
echo zip stop time=%zip_stop_time%

set /A zip_duration = zip_stop_time-zip_start_time
echo Zip duration=%zip_duration%

set overall_stop_time=%time:~0,2%%time:~3,2%
set /A overall_duration = overall_stop_time-overall_start_time
echo Overall duration: %overall_duration%

:end