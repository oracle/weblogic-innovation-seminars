This demo was created from original work at:

http://svn.apache.org/viewvc/tomcat/trunk/webapps/examples/WEB-INF/classes/websocket/snake/

The intent is to show an existing demonstration of a WebSocket application
running on WebLogic Server using its WebSocket API and programming model.

The WebLogic WebSocket API is provided within the 
$MW_HOME/wlserver/modules/com.bea.core.weblogic.web.api_3.0.0.0.jar file.

To build applications using the WebLogic WebSocket API, this library must be in 
the classpath when the application is being compiled.

To use Maven to build an application using the WebLogic WebSocket API, the 
simplest way to use the API is to manually install the library as a Maven 
artifact into a local repository and then declare it as dependency w
ithin the projects POM file.

The maven install command:

$ mvn install:install-file 
–Dfile= $MW_HOME/wlserver/modules/com.bea.core.weblogic.web.api_3.0.0.0.jar 
-DgroupId=com.bea.core.weblogic.web 
-DartifactId=api
-Dversion=12.1.2.0
-Dpackaging=jar

The pom.xml dependency declaration: 

<dependency>
    <groupId>com.bea.core.weblogic.web</groupId>
    <artifactId>api</artifactId>
    <version>12.1.2.0</version>
    <type>jar</type>
    <scope>provided</scope>
</dependency>



