### Introduction ###

#### Domain to Partition Conversion tool ####
The Domain to Partition Conversion Tool (DPCT) provides a utility that inspects a specified source domain and produces an archive containing the resources, deployed applications and other settings.  This can then be used with the Import Partition operation provided in WebLogic Server 12.2.1 to create a new partition that represents the original source domain.  An external overrides file is generated (in JSON format) that can be modified to adjust the targets and names used for the relevant artifacts when they are created in the partition. DPCT supports WebLogic Server 10.3.6, 12.1.1, 12.1.2 and 12.1.3 source domains and makes the conversion to WebLogic Server 12.2.1 partitions a straightforward process.

#### Lift and shift ####
During our labs we show how to move existing domain partition running in On-Premise WebLogic Server by export and import capabilities to WebLogic Server running inside Java Cloud Service. We prepared single maven command that copy domain partition files in to Java Cloud Service VM as well as copy and execute all SQL files on DataBase Cloud Service environment to create working environment for our lab. In this HOL we keep domain and database configuration simple.

----

### Prerequisites ###

This lab require to build a model of on-premise enviornment. There are two options how you can prepare that environment:

+ By importing distributed by Oracle Virtual Box image. Contact your local oracle representative or Maciej.Gruszka@oracle.com
+ By creation your own nevironment

#### How to create own environment ###
You need to install the following software:

Software | Expected path | Remarks
--- | --- | ---
JDK7 | /usr/java/jdk1.7.0_XX/ | We used JDK7u79 so the path in the VBox was /usr/java/jdk1.7.0_79/
JDK8 | /usr/java/jdk1.8.0_XX/ | We used JDK8u60 so the path in the VBox was /usr/java/jdk1.8.0_60/
Maven |  | 
Lab content | /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8 | Extracted based on the content at https://github.com/oracle-weblogic/weblogic-innovation-seminars/tree/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8
cloud.demos utility content | /u01/content/weblogic-innovation-seminars/cloud.demos | Extracted based on the content at https://github.com/oracle-weblogic/weblogic-innovation-seminars/tree/caf-12.2.1/cloud.demos
Weblogic Server 10.3.6 | /u01/wins/wls1036/ | We used WLS 10.3.6.0.0
WebLogic Server 12.2.1 | /u01/wins/wls1221/ | We used WLS 12.2.1.0.0
Domain To Partition  Conversion Tool | /u01/dpct |
Oracle DataBase 12c | /u01/app/oracle/product/12.1.0/dbhome_1/ | 

#### Modifying environment.properties file ###
To operate all scripts agains your own Oracle Public Cloud environment you need to set up file environment.properties in the /u01/content/weblogic-innovation-seminars/cloud.demos folder.
We have prepared two samples environment.properties.us2 and environment.properties.emea2 that you could customize and copy to environment.properties file.

The table below describes the meaning of viariables stored in that file
TBD - table with veriables

If your OPC account is just fresh created account - no storage, no DBCS instance, no JCS instance - then modify in the environment.properties only three variables:
+ opc.identity.domain
+ opc.username
+ opc.password
and execute the following scripts:

    $ [oracle@localhost Desktop]$ cd /u01/content/weblogic-innovation-seminars/cloud.demos
    $ [oracle@localhost cloud.demos]$ mvn install -Dgoal=generate-ssh-keypair
      (...) 
    $ [oracle@localhost cloud.demos]$ mvn install -Dgoal=jcs-create-auto
      (...)

It would create for you all needed instances on JCS and DBCS


----
