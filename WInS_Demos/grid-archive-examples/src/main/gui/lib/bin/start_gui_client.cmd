@@echo off

rem Start the GUI Client

rem


rem setDomainEnv.sh must be run from your domain
rem

set GUI_HOME=%~dp0\..


SET CP=%GUI_HOME%\lib\ExampleGUI.jar;%GUI_HOME%\config;%GUI_HOME%\lib\ExampleGAR.gar;%CLASSPATH%



%JAVA_HOME%\bin\java.exe -cp %CP% -Xmx128m -Xms128m -Dtangosol.coherence.tcmp.enabled=false -Dtangosol.coherence.cacheconfig=extend-cache-config.xml -Dtangosol.pof.enabled=true -Dtangosol.pof.config=META-INF/pof-config.xml weblogic.coherence.application.gui.ContactListenerGUI
