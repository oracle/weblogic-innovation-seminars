# MULTITENANCY WORKSHOP

## INTRODUCTION:

### Domain to Partition Conversion tool:

The **Domain to Partition Conversion Tool** (DPCT) provides a utility that inspects a specified source domain and produces an archive
containing the resources, deployed applications and other settings.  This can then be used with the **Import Partition** operation 
provided in WebLogic Server 12.2.1 to create a new partition that represents the original source domain.  An external overrides file
is generated (in JSON format) that can be modified to adjust the targets and names used for the relevant artifacts when they are
created in the partition. DPCT supports WebLogic Server 10.3.6, 12.1.1, 12.1.2 and 12.1.3 source domains and makes the conversion
to WebLogic Server 12.2.1 partitions a straightforward process.

DPCT is available for downloaded from OTN:
[http://www.oracle.com/technetwork/middleware/weblogic/downloads/wls-main-097127.html](http://www.oracle.com/technetwork/middleware/
weblogic/downloads/wls-main-097127.html)

**Note:** there is also a corresponding patch (**opatch**) posted alongside the DPCT download that needs to be downloaded and applied to
the target installation of WebLogic Server 12.2.1 to support the import operation.

The README contains more details and examples of using the tool:
[http://download.oracle.com/otn/nt/middleware/12c/1221/wls1221_D-PCT-README.txt]
(http://download.oracle.com/otn/nt/middleware/12c/1221/wls1221_D-PCT-README.txt)

A video demonstration of using DPCT to convert a WebLogic Server 12.1.3 domain with a deployed application into a WebLogic Server
12.2.1 is also available on our YouTube channel: [https://youtu.be/D1vQJrFfz9Q](https://youtu.be/D1vQJrFfz9Q)

### Lift and shift:

In this case, we shows if you have domain partition running in On-Premise WebLogic Server, then how using a single maven command,
you can run this partition on domain running in Java Cloud Service. This Workshop shows both the domain and database configuration,
as this is Workshop, we keep domain and database configuration as simple.

## PRE-REQUISITE:

1. You must have access to INTERNET.
2. In this, we create the required environment. We expect the user to run the command in pre-requisite before he comes to the
workshop , as this steps are time taking.

**Preparation of environment:**

You need to modify the **environment.properties.MT** file present in **/u01/content/weblogic-innovation-seminars/cloud.demos**
folder. We expect the user to modify the following parameter values **opc.identity, opc.username, opc.password** and
**ssh.passphrase**, you can modify the values of other parameter as well, but in order to keep things simple, modify only these four
values and then you need to rename the file with new name **environment.properties**.
 
**Creation of pair of SSH Key:**

1. cd /u01/content/weblogic-innovation-seminars/cloud.demos/
2. **mvn install -Dgoal=generate-ssh-keypair**
3. Change the pk.openssh key permission to 600: **chmod 600 pk.openssh**

**Creation of Storage Container, DBCS and JCS Instance:**

1. We assume that it is your fresh oracle cloud account, and you didn’t perform any operation on it, so we will start from scratch.
As we have single maven command for creating Cloud storage container, DBCS and JCS which takes more than 50 minutes, you can refer
the **README.md** file available in [https://github.com/oracle-weblogic/weblogic-innovation-seminars/tree/caf-12.2.1/cloud.demos]
(https://github.com/oracle-weblogic/weblogic-innovation-seminars/tree/caf-12.2.1/cloud.demos) for more information.
2. **mvn install -Dgoal=jcs-create-auto** (This maven commands, first creates storage container and after that it create winsdemo as
DBCS instance, and later, it creates winsdemoWLS as JCS instance. Your output of this command should like similar to below.)
3. **mvn install -Dgoal=dbcs-get-instance-details** (You need to Note down the DBCS Public IP, we need to use it later.)
4. **mvn install -Dgoal=jcs-get-instance-details** (You need to Note down the JCS Public IP, we need to use it later.)
5. The above steps creates a domain **winsdemoWLS_domain** inside the JCS VM, and pluggable database **PDB1.{Value of opc.identity
from environment.properties}.oraclecloud.internal**. A WebLogic domain contains the cluster **winsdemoWLS_cluster** with
**winsdemo_adminserver** as Admin Server and **winsdemo_server_1** as managed server.

## Virtual Box Environment

### The Hands on Lab Environment

**Operating System Details**

Variable | Value
-------- | ---------
Operating System | Oracle Linux 6.4 x86_64
Hostname | localhost, wins-vbox
Root User | Root User
Oracle User | oracle/welcome1

Note:  For this hand on lab you should only need to use **oracle** user account.

**Installation Directories**	

Variable | Value
--------------- | -------------------
JDK 1.8.0_60 | /usr/java/jdk1.8.0_60/
JDK 1.7.0_79 | /usr/java/jdk1.7.0_79/
WebLogic Server 12.2.1 | /u01/wins/wls1221/
Oracle Traffic Director 12.2.1 | /u01/wins/wls1221/
Oracle Database 12c | /u01/app/oracle/product/12.1.0/dbhome_1/

**Workshop Content:**

Variable | Value
------- | --------
Labs Directory | /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/
cloud.demos directory | /u01/content/weblogic-innovation-seminars/cloud.demos


## LAB: DOMAIN TO PARTITION CONVERSION TOOL

### OVERVIEW:

In this lab, we are going to perform the below operation:
* We have already created **Domain1036** in **/u01/wins/wls1036/user_projects/domains/Domain1036**. This domain contains Admin Server
only, we created **PetstoreDB** as Datasource and Deployed **JSF-2.0.war** as shared library and deployed **petstore.12.war** as an
application to the Admin Server. This is the **source domain**, which we are going to **Export as a partition**, which will create a
**Domain1036.zip** and **Domain1036-attributes.json** file.
* We have already created **Domain1221** in **/u01/wins/wls1221/user_projects/domains/Domain1221**. This is vanilla domain, which
contains Admin Server only. This will be our **target domain**, where we will **import** the previous domain **Domain1036 as
partition**.

### STARTING THE ADMIN SERVER IN BOTH DOMAINS:

1. cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8
2. **./prepare_env.sh** (This commands perform the below operations)
 * It starts the pluggable database **pdborcl**.
 * It creates the **petstore** user in **pdborcl** database, and populates the database with sample data.
 * It sets the **JAVA_HOME** to **/usr/java/jdk1.7.0_79/** and **MW_HOME** to **/u01/wins/wls1036/** then it starts the Admin Server
 in Domain1036.
 * Then it sets the **JAVA_HOME** to **/usr/java/latest/** (JDK 8) and **MW_HOME** to **/u01/wins/wls1221/** then it starts the
 Admin Server in **Domain1221**.

![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/1.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/2.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/3.PNG)
3. Go to browser and access the application URL on
[http://localhost:6001/petstore/faces/catalog.jsp](http://localhost:6001/petstore/faces/catalog.jsp). This is Pet Store application;
you can click on different Animal name like **Cats, Dogs** and **Birds**. You can also click on **Seller** and **Search** for
verification of application Execution. This application is build on using **Java EE 5 features**.

![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/4.PNG)

### RUNNING DOMAIN TO PARTITION CONVERSION TOOL:

1. We have put the Domain to Partition conversion tool in **/u01/** folder.
2. Go back to terminal.
3. echo **$JAVA_HOME** (It should be **/usr/java/latest**, which is JDK 8 required for DPCT tool to run.)
4. cd /u01/dpct
5. **./exportDomainForPartition.sh /u01/wins/wls1036/ /u01/wins/wls1036/user_projects/domains/Domain1036/**
(This command creates the **Domain1036.zip** and **Domain1036-attributes.json** file in **/u01/dpct/outDir/** folder)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/5.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/6.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/7.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/8.PNG)

### IMPORT DOMAIN AS PARTITION IN WEBLOGIC 12.2.1 MULTITENANT ENVIRONMENT:
1. Go to the browser and type the URL for **Admin Console of Domain1221 domain**
[http://localhost:9001/console](http://localhost:9001/console). 
2. Enter **weblogic/welcome1** as **Username/Password** then click on **Login**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/9.PNG)
3. In **Domain Structure**, Click on **Domain Partitions**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/10.PNG)
4. Click on **Import**, Specify **Microcontainer1** as **Domain Partition name** and **/u01/dpct/outDir/Domain1036.zip** as **Path**
then click on **OK**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/11.PNG)
5. Click on **Environment -> Virtual Targets**, and then click on **Microcontainer1-AdminServer-virtualTarget**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/12.PNG)
6. Modify the **URI Prefix** to **/** and set **Port Offset** to **100** and then click on **Save**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/13.PNG)
7. Click on **Deployments**, as **jsf-2.0.war** is already bundled with WebLogic Server 12.2.1 installation, we don’t need it
specifically. Check the box for **jsf(2.0,1.0.0.0_2-0-2)** and then click on **Delete**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/14.PNG)
8. Click on **Domain Partitions** and then click on **Control** tab, check the box for **Microcontainer1** and then click on **Start**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/15.PNG)
9. Wait for 5-6 seconds, and then click on **Configuration** tab of **Domain Partitions**. Once this partition is in **RUNNING**
State, and then only goes to next step.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/16.PNG)
10. Access the Application on [http://localhost:9101/petstore/faces/catalog.jsp](http://localhost:9101/petstore/faces/catalog.jsp).
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/17.PNG)


## LAB: LIFT AND SHIFT

### OVERVIEW:

Exporting and importing partitions let WLS system administrators easily move partitions from one domain to another, including the
applications that are deployed to the partition. This feature is useful for replicating partitions across the domains and for moving
domains from a development to a testing then production environment.

Exporting a domain partition creates a partition backup and stores it in an archived format. You can export a domain partition from
source domain with its entire configuration and data. With few configuration changes, you can then import the partition archive into
another instance of multi-tenant WLS (the target domain). You might need to update any domain dependencies, such as targets and
security realms, and optionally update other attributes in the partition configuration to make it valid.

When a partition is exported from the source domain it is packaged in a partition archive. Included in the partition archive is:
* The partition configuration.
* Any resource group contained in the partitions.
* Any resource group templates referred to by those resource groups. 
*	The contents of the file system, <partition-file-system>/config directory. 
* Optionally, application binaries and configuration for applications deployed to the partition. 

No application runtime state or application-specific runtime configuration is included in the partition archive. Examples of what
would not be exported are JMS messages in queues, users in an embedded LDAP realm.

In this Lab, We are going to perform the below operations:
* Export a domain partition from a vanilla domain in development mode in on premise environment.
*	Import the domain partition to a domain which is running in production mode and also in Java Cloud Service.

### EXPORT PARTITION FROM ON PREMISE DOMAIN1221:

1. In Domain1221 Admin Console, Click on **Domain Partitions**.
2. Check the box for **Microconatiner1** and then click on **Export**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/18.PNG)
3. Check the box for **Include Application Bits**, and then enter **/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8/JCS**
as **Path** and then click on **OK**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/19.PNG)
4. Go back to terminal, and verify the creation of **Microcontainer1.zip** and **Microcontainer1-attributes.json**. These files, we
need while importing the partition in winsdemoWLS_domain in Java Cloud Service.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/20.PNG)
5. cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab2
6. **mvn install -DLab2 -Djcs.ip={PUBLIC IP OF JCS INSTANCE} -Ddbcs.ip={PUBLIC IP OF DBCS INSTANCE}**
(We have **MultitenancyWorkshop.PDF** available in the **Desktop**, In that workshop we create three domain partitions as part of
Lab2. We are performing the same step in this workshop also, It showed how you can have **multiple partition** in a **same domain**,
where each partition may be generated from Domain of **WebLogic 10.3.6, WebLogic 12.1.1, WebLogic 12.1.2  and WebLogic 12.1.3**
through **Domain to Partition Conversion tool**.)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/21.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/22.PNG)
7. cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8
8. **mvn install -DLiftAndShift -Djcs.ip={PUBLIC IP OF JCS INSTANCE} -Ddbcs.ip={PUBLIC IP OF DBCS INSTANCE}**
(The above maven command copies the required files to DBCS instance first, and populate the database with sample data. Here we used
the SQL scripts for that, In general, you can unplug the local database, copies related files to DBCS instance and then plug
the pluggable database to database in DBCS or you can export and import database. But to simply the demo, we used the SQL scripts
here. Then it copies the ZIP and JSON file to **/tmp** folder of JCS instance, and provides it sufficient permissions. It also
creates the Virtual Target **Microcontainer1-AdminServer-virtualTarget**, which we will use during the import partition.)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/23.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/24.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/25.PNG)

### IMPORTING PARTITION IN JAVA SERVICE CLOUD:

1. Go to Admin Console of JCS instance
[https://{PUBLIC_IP_OF_JCS_INSTANCE}:7002/console](https://{PUBLIC_IP_OF_JCS_INSTANCE}:7002/console) and enter
**weblogic/welcome1** as **Username/Password** and then click on **Login**. If you face any issue in opening the admin console
in Virtual box, you can open your host machine browser, to open the Admin Console URL.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/30.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/31.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/32.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/33.PNG)
2. Click on **Domain Partitions** and then click on **Lock & Edit**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/34.PNG)
3. Click on **Import**, specify the file **/tmp/Microcontainer1.zip** in **Path** and click on **OK**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/35.PNG)
4. Click on **Lock & Edit**, and then click on **Services -> Data Sources -> PetstoreDB**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/36.PNG)
5. Click on the **Connection Pool** tab for **PetstoreDB** datasource, and modify the URL **jdbc:oracle:thin:@localhost:1521/pdborcl**
with **jdbc:oracle:thin:@DBCS_INSTANCE_NAME:1521/ PDB1.{Value of opc.identity from 	environment.properties file}.oraclecloud.internal**
and then click on **Save**. Click on **Activate Changes**. Where **DBCS _INSTANCE_NAME** should be **winsdemo**, if you did not
modify it in 	**environment.properties** file, and **opc.identity** name is your identity domain name, you specified
in **environment.properties** file.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/37.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/38.PNG)
6. Click on **Domain Partitions**, go to **Control** tab, Check the box near **Microcontainer1** partition and then click on *Start**.
On Confirmation screen click on **Yes**.
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/39.PNG)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/40.PNG)
7. Click on the refresh icon, once the partition is in **RUNNING** state, go to the browser and access the application
[http://{PUBLIC_IP_OF_JCS_INSTANCE}/petstore/faces/catalog.jsp](http://{PUBLIC_IP_OF_JCS_INSTANCE}/petstore/faces/catalog.jsp)
![alt text](https://raw.githubusercontent.com/oracle-weblogic/weblogic-innovation-seminars/caf-12.2.1/WInS_Demos/MT-Workshop/Lab8/md.resources/41.PNG)
Here we showed how you can lift and shift the partition running in development mode in on premise to production mode domain in Java Cloud Service.

## CONCLUSION:
1. We took a domain Domain1036 created on WebLogic 10.3.6 Server, We used the Domain to Partition Conversion tool, to create archive
and JSON file, which we later used for importing as partition in Domain1221 created on WebLogic 12.2.1 server. So in that way,
Customer who has domain running in previous version of WebLogic Server, can be easily migrated to WebLogic Server 12.2.1 as
partition.
2. Exporting and Importing Partitions let WLS system administrators easily move partition from one domain to another, including the
applications that are deployed to the partition. This features in useful for replicating partition across the domains and for moving
domains from a development to a testing and then in production environment. Here we showed How you can replicate the partition
running in development mode in on-premise to domain running in production mode in Java Cloud Service instance








