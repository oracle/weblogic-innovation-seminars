@echo off

set overall_start_time=%time:~0,2%%time:~3,2%
set dt=%date:~-4%%date:~4,2%%date:~7,2%_%time:~0,2%%time:~3,2%
echo date_file=%dt%

set OVA_PRODUCT_URL=http://retriever.us.oracle.com/apex/f?p=121:3:2027314285611264::NO:RP:P3_PAGE_ID:2129
set OVA_VERSION=12.1.2-v4
set OVA_EULA_FILE=Z:\jeffreyawest\Data\mycode\github\oracle-weblogic\weblogic-innovation-seminars\WInS_Demos\control\vbox\eula.txt

set VBOX_NAME=%1
set VBOX_OUTPUT_NAME=wins-%OVA_VERSION%.ova
set VBOX_OUTPUT_DIR=%3
set VBOX_ZIP_OUTPUT_DIR=%4

echo Exporting VM=[%VBOX_NAME%] to OVA=[%VBOX_OUTPUT_NAME%] in directory=[%VBOX_OUTPUT_DIR%]
echo Zip output dir=[%ZIP_OUTPUT_DIR%]

set ZIP_CLI_CMD=c:\data\7-zip\7z.exe
set ZIP_METHOD=zip
SET ZIP_COMPRESSION_LEVEL=1
SET ZIP_MAX_SIZE=1g
SET ZIP_DEST_FILE=C:\temp\%VBOX_OUTPUT_NAME%.zip

echo Exporting VM=[%VBOX_NAME%] to OVF=[%VBOX_OUTPUT_NAME%] in directory=[%VBOX_OUTPUT_DIR%]

mkdir %VBOX_OUTPUT_DIR%

set export_start_time=%time:~0,2%%time:~3,2%
echo export start time=%export_start_time%
set EXPORT_OPTS=%VBOX_NAME%
set EXPORT_OPTS= %EXPORT_OPTS% --output %VBOX_OUTPUT_DIR%/%VBOX_OUTPUT_NAME%
set EXPORT_OPTS= %EXPORT_OPTS% --vsys 0 
set EXPORT_OPTS= %EXPORT_OPTS% --product WInS-VirtualBox-VM
set EXPORT_OPTS= %EXPORT_OPTS% --producturl %OVA_PRODUCT_URL%
set EXPORT_OPTS= %EXPORT_OPTS% --version %OVA_VERSION%
set EXPORT_OPTS= %EXPORT_OPTS% --eulafile %OVA_EULA_FILE%


echo "c:\Program Files\Oracle\VirtualBox\VBoxManage.exe" export %EXPORT_OPTS%

"c:\Program Files\Oracle\VirtualBox\VBoxManage.exe" export %EXPORT_OPTS%


set export_stop_time=%time:~0,2%%time:~3,2%
echo export stop time=%export_stop_time%

set /A export_duration = export_stop_time-export_start_time
echo export duration=%export_duration%

set zip_start_time=%time:~0,2%%time:~3,2%
echo zip start time=%zip_start_time%

echo %ZIP_CLI_CMD% a -t%ZIP_METHOD% -mx%ZIP_COMPRESSION_LEVEL% -v%ZIP_MAX_SIZE% %ZIP_DEST_FILE% %VBOX_OUTPUT_DIR%/%VBOX_OUTPUT_NAME%

%ZIP_CLI_CMD% a -t%ZIP_METHOD% -mx%ZIP_COMPRESSION_LEVEL% -v%ZIP_MAX_SIZE% %ZIP_DEST_FILE% %VBOX_OUTPUT_DIR%/%VBOX_OUTPUT_NAME%

set zip_stop_time=%time:~0,2%%time:~3,2%
echo zip stop time=%zip_stop_time%

set /A zip_duration = zip_stop_time-zip_start_time
echo Zip duration=%zip_duration%

set overall_stop_time=%time:~0,2%%time:~3,2%
set /A overall_duration = overall_stop_time-overall_start_time
echo Overall duration: %overall_duration%
