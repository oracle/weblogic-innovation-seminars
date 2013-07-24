echo Assembling the Virtual Box images for WInS VM
echo.
echo Producing VDD_WLS_labs_2012.ova from VDD_WLS_labs_2012.1 - VDD_WLS_labs_2012.6
echo.

set BASE_NAME=wins-12.1.2-v5.ova
set COUNT=9



set CMDLINE=copy /B
set CMDLINE=%CMDLINE% %BASE_NAME%.ova.z01

:loopstart

set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z02
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z03
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z04
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z05
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z06
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z07
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z08
set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.z09

if

set CMDLINE=%CMDLINE% + %BASE_NAME%.ova.zip
set CMDLINE=%CMDLINE% %BASE_NAME%.ova-final.zip

%CMDLINE%

dir *.ova
echo.
echo Done