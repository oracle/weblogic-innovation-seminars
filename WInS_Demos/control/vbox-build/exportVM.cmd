@echo off

set overall_start_time=%time:~0,2%%time:~3,2%
set dt=%date:~-4%%date:~4,2%%date:~7,2%_%time:~0,2%%time:~3,2%
echo date_file=%dt%

set VBOX_NAME_INPUT=%1
set VBOX_NAME_NEW=%2
set VBOX_OUTPUT_DIR=%3
set VBOX_ZIP_OUTPUT_DIR=%4

rem  pause

echo Exporting VM=[%VBOX_NAME_INPUT%] to OVA=[%VBOX_NAME_NEW%] in directory=[%VBOX_OUTPUT_DIR%]
echo Zip output dir=[%VBOX_ZIP_OUTPUT_DIR%]

set OVA_PRODUCT_URL=http://retriever.us.oracle.com/apex/f?p=121:3:2027314285611264::NO:RP:P3_PAGE_ID:2129
set OVA_EULA_FILE=Z:\jeffreyawest\Data\mycode\github\oracle-weblogic\weblogic-innovation-seminars\WInS_Demos\control\vbox\eula.txt
set VBOXMANAGE_EXE=C:\Progra~1\Oracle\VirtualBox\VBoxManage.exe
set VBOX_OUTPUT_OVA=%VBOX_NAME_NEW%.ova


set ZIP_CLI_CMD=c:\data\7-zip\7z.exe
set ZIP_METHOD=zip
SET ZIP_COMPRESSION_LEVEL=2
SET ZIP_MAX_SIZE=1g
SET ZIP_DEST_FILE=%VBOX_ZIP_OUTPUT_DIR%\%VBOX_OUTPUT_OVA%.zip

%VBOXMANAGE_EXE% list runningvms |findstr /R /C:"%VBOX_NAME_INPUT%"
set STILL_RUNNING=%ERRORLEVEL%

if %STILL_RUNNING% == "0" (
  echo "VM is running please stop before continuing."
  exit
  goto end
)

rem rename so new version is in the list of VMs
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_INPUT% --name "%VBOX_NAME_NEW%"

rem update the network for the non-internal NIC to NAT
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_NEW% --nic1 nat

rem set CPU and memory to a reasonable amount
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_NEW% --memory 6144
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_NEW% --cpus 4

rem remove USB so you don't need the USB extensions
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_NEW% --usb off

%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_NEW% --ostype Oracle_64
%VBOXMANAGE_EXE% modifyvm %VBOX_NAME_NEW% --clipboard bidirectional

rem remove shared folders
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "jeffreyawest" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "opt" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "temp" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "tmp" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "github" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "mycode" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "oracle-parcel-service" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "oracle-weblogic" > NUL
%VBOXMANAGE_EXE% sharedfolder remove %VBOX_NAME_NEW% --name "weblogic-innovation-seminars" > NUL

echo ERRORS ABOVE ARE OK!  This simply means the shared folders were not mounted!

mkdir %VBOX_OUTPUT_DIR%

set export_start_time=%time:~0,2%%time:~3,2%
echo export start time=%export_start_time%
set EXPORT_OPTS=%VBOX_NAME_NEW%
set EXPORT_OPTS= %EXPORT_OPTS% --output %VBOX_OUTPUT_DIR%/%VBOX_OUTPUT_OVA%
set EXPORT_OPTS= %EXPORT_OPTS% --vsys 0 
set EXPORT_OPTS= %EXPORT_OPTS% --product WInS-VirtualBox-VM
set EXPORT_OPTS= %EXPORT_OPTS% --producturl %OVA_PRODUCT_URL%
set EXPORT_OPTS= %EXPORT_OPTS% --version %VBOX_NAME_NEW%
set EXPORT_OPTS= %EXPORT_OPTS% --eulafile %OVA_EULA_FILE%


echo %VBOXMANAGE_EXE% export %EXPORT_OPTS%

%VBOXMANAGE_EXE% export %EXPORT_OPTS%


set export_stop_time=%time:~0,2%%time:~3,2%
echo export stop time=%export_stop_time%

set /A export_duration = export_stop_time-export_start_time
echo export duration=%export_duration%

set zip_start_time=%time:~0,2%%time:~3,2%
echo zip start time=%zip_start_time%


set ZIP_CLI_OPTS=-tzip
set ZIP_CLI_OPTS=%ZIP_CLI_OPTS% -mx=1 -md=512m
set ZIP_CLI_OPTS=%ZIP_CLI_OPTS% -mmt=4 -mfb=258 -mpass=2
set ZIP_CLI_OPTS=%ZIP_CLI_OPTS% -v%ZIP_MAX_SIZE%

echo %ZIP_CLI_CMD% a %ZIP_CLI_OPTS% %ZIP_DEST_FILE% %VBOX_OUTPUT_DIR%\%VBOX_OUTPUT_OVA%
%ZIP_CLI_CMD% a %ZIP_CLI_OPTS% %ZIP_DEST_FILE% %VBOX_OUTPUT_DIR%/%VBOX_OUTPUT_OVA%

set zip_stop_time=%time:~0,2%%time:~3,2%
echo zip stop time=%zip_stop_time%

set /A zip_duration = zip_stop_time-zip_start_time
echo Zip duration=%zip_duration%

set overall_stop_time=%time:~0,2%%time:~3,2%
set /A overall_duration = overall_stop_time-overall_start_time
echo Overall duration: %overall_duration%

:end