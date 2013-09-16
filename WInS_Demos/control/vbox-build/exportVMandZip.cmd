@echo off

set overall_start_time=%time:~0,2%%time:~3,2%
rem set dt=%date:~-4%%date:~4,2%%date:~7,2%_%time:~0,2%%time:~3,2%
rem echo date_file=%dt%

rem *** Set local env variables ************************************************************

set WINS_DEMOS_DIR=Z:\jeffreyawest\code\mycode\github\oracle-weblogic\weblogic-innovation-seminars\WInS_Demos
set JAVA_HOME=C:\PROGRA~1\Java\jdk1.7.0_40
set VBOXMANAGE_EXE=C:\Progra~1\Oracle\VirtualBox\VBoxManage.exe
set ZIP_CLI_CMD=c:\progra~1\7-Zip\7z.exe

rem *** Set script variables ************************************************************

set VBOX_BUILD_DIR=%WINS_DEMOS_DIR%\control\vbox-build
set VBOX_MEM=6144
set VBOX_CPU=4
set VBOX_PRODUCT_URL=https://stbeehive.oracle.com/teamcollab/library/st/WebLogic+Innovations+Seminar/Public+Documents/WInS-VMs/WInS
set VBOX_EULA_FILE=%VBOX_BUILD_DIR%\eula.txt

set ZIP_METHOD=zip
set ZIP_MAX_SIZE=2g


rem *** Get parameters... ************************************************************

echo.
echo === Listing Current VMs ========================================================
echo.

%VBOXMANAGE_EXE% list vms
echo.

set /p VBOX_NAME_SOURCE=Enter VM Name to export (i.e. wins-12.1.2-v5.43):
set /p VBOX_NAME_DEST=Enter the new VM name (increment the version, i.e. wins-12.1.2-v5.44):
set /p VBOX_OUTPUT_DIR=Enter the base directory for the OVA and ZIP files:

rem set VBOX_NAME_SOURCE=wins-12.1.2-v5.43-2
rem set VBOX_NAME_DEST=wins-12.1.2-v5.43-2
rem set VBOX_OUTPUT_DIR=c:\jeffreyawest\tmp

set VBOX_OUTPUT_DIR=%VBOX_OUTPUT_DIR%\%VBOX_NAME_DEST%
set VBOX_ZIP_OUTPUT_DIR=%VBOX_OUTPUT_DIR%\7zip
set VBOX_OUTPUT_OVA=%VBOX_OUTPUT_DIR%\%VBOX_NAME_DEST%.ova

echo.
echo === Confirm Parameters =======================================================
echo.

echo VBOX_NAME_SOURCE:    %VBOX_NAME_SOURCE%
echo VBOX_NAME_DEST:      %VBOX_NAME_DEST%
echo VBOX_OUTPUT_DIR:     %VBOX_OUTPUT_DIR%
echo VBOX_ZIP_OUTPUT_DIR: %VBOX_ZIP_OUTPUT_DIR%
echo VBOX_OUTPUT_OVA:     %VBOX_OUTPUT_OVA%
echo.
echo.

set /p PROMPT_CONTINUE=Please confirm the above settings and enter 'y' to continue [y/n]:

if "x%PROMPT_CONTINUE%x" == "xyx" (
  echo Continuing...
) else (
  echo Aborting...
  goto end
)

echo.
echo === Compiling Java MD5 Program =================================================
echo.

%JAVA_HOME%\bin\javac -d %VBOX_BUILD_DIR% %VBOX_BUILD_DIR%\ChecksumTool.java

set MD5_CMD=%JAVA_HOME%\bin\java -cp %VBOX_BUILD_DIR% ChecksumTool
echo MD5_CMD=%MD5_CMD%

echo.
echo === Checking VM Status =========================================================
echo.

%VBOXMANAGE_EXE% list runningvms | findstr /R /C:"%VBOX_NAME_SOURCE%"
set STILL_RUNNING=%ERRORLEVEL%

if %STILL_RUNNING% == "0" (
  echo VM is running please stop before continuing.
  goto end
)

echo.
echo === Modifying VM parameters Before Export ======================================
echo.

rem rename so new version is in the list of VMs
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_SOURCE% --name "%VBOX_NAME_DEST%"

rem update the network for the non-internal NIC to NAT
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_DEST% --nic1 nat

rem set CPU and memory to a reasonable amount
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_DEST% --memory %VBOX_MEM%
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_DEST% --cpus %VBOX_CPU%

rem remove USB so you don't need the USB extensions
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_DEST% --usb off

%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_DEST% --ostype Oracle_64
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_DEST% --clipboard bidirectional

rem remove shared folders
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "jeffreyawest" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "opt" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "temp" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "tmp" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "github" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "mycode" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "oracle-parcel-service" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "oracle-weblogic" > NUL 2>&1
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_DEST% --name "weblogic-innovation-seminars" > NUL 2>&1

rem echo !?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?
rem echo ERRORS ABOVE ARE OK!  This simply means the shared folders were not mounted!
rem echo ERRORS ABOVE ARE OK!  This simply means the shared folders were not mounted!
rem echo ERRORS ABOVE ARE OK!  This simply means the shared folders were not mounted!
rem echo !?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?

echo.
echo === Creating Directories =======================================================
echo.

echo Creating OVA Output directory: %VBOX_OUTPUT_DIR%
mkdir %VBOX_OUTPUT_DIR%

echo Creating ZIPPED OVA Output directory: %VBOX_ZIP_OUTPUT_DIR%
mkdir %VBOX_ZIP_OUTPUT_DIR%


echo.
echo === Exporting VM ===============================================================
echo.

echo Exporting VM=[%VBOX_NAME_SOURCE%] to OVA=[%VBOX_NAME_DEST%] in directory=[%VBOX_OUTPUT_DIR%]

set EXPORT_OPTS=%VBOX_NAME_DEST%
set EXPORT_OPTS= %EXPORT_OPTS% --output %VBOX_OUTPUT_OVA%
set EXPORT_OPTS= %EXPORT_OPTS% --vsys 0
set EXPORT_OPTS= %EXPORT_OPTS% --product WInS-VirtualBox-VM
set EXPORT_OPTS= %EXPORT_OPTS% --producturl %VBOX_PRODUCT_URL%
set EXPORT_OPTS= %EXPORT_OPTS% --version %VBOX_NAME_DEST%
set EXPORT_OPTS= %EXPORT_OPTS% --eulafile %VBOX_EULA_FILE%

set CMDLINE=%VBOXMANAGE_EXE% export %EXPORT_OPTS%

call:timedCommand

if "%COMMAND_SUCCESS%" NEQ "0" (
  echo EXPORT Failed for some reason, please see messages above!
  goto end
)

rem %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

echo.
echo === Creating OVA MD5 ===========================================================
echo.
echo Creating MD5 for OVA File: %VBOX_OUTPUT_OVA%

set CMDLINE=%MD5_CMD% %VBOX_OUTPUT_OVA%

call:timedCommand

rem %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

echo.
echo === Zipping OVA ================================================================
echo.
echo Zipping OVA=[%VBOX_NAME_DEST%] into output dir=[%VBOX_ZIP_OUTPUT_DIR%]

set ZIP_CLI_OPTS=a -tzip -mx=0 -md=512m -mmt=4 -mfb=258 -mpass=15 -v%ZIP_MAX_SIZE%
echo ZIP_CLI_OPTS=%ZIP_CLI_OPTS%

set CMDLINE=%ZIP_CLI_CMD% %ZIP_CLI_OPTS% %VBOX_ZIP_OUTPUT_DIR%\%VBOX_NAME_DEST%.zip %VBOX_OUTPUT_OVA% %VBOX_OUTPUT_OVA%.md5

call:timedCommand

if "%COMMAND_SUCCESS%" NEQ "0" (
  echo Split-zipping of files FAILED!
  goto end
)

rem %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

echo.
echo ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
echo === Creating MD5 of Split-Zips =================================================
echo.
echo Creating MD5 for Zipped OVA Files in directory: %VBOX_ZIP_OUTPUT_DIR%"

set CMDLINE=%MD5_CMD% %VBOX_ZIP_OUTPUT_DIR%

call:timedCommand

if "%COMMAND_SUCCESS%" NEQ "0" (
  echo MD5 of split-ZIP files failed!
)

:end

set overall_stop_time=%time:~0,2%%time:~3,2%
set /A overall_duration = overall_stop_time-overall_start_time
echo Total duration: %overall_duration% minute(s)

rem %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

GOTO:EOF

:timedCommand
set command_start_time=%time:~0,2%%time:~3,2%

echo Command start time: %command_start_time%

echo --------------------------
echo Executing command: %CMDLINE%

%CMDLINE%

set COMMAND_SUCCESS=%ERRORLEVEL%
echo COMMAND_SUCCESS: %COMMAND_SUCCESS%

set command_stop_time=%time:~0,2%%time:~3,2%
echo Command stop time=%command_stop_time%
echo --------------------------

set /A command_duration = command_stop_time-command_start_time
echo Command Duration: %command_duration% minute(s)

GOTO:EOF

