# ORACLE Public Cloud Services tutorial #
-----
## Move partition from WebLogic Server 12cR2 to Java Cloud Service ##

### About this tutorial ###

This part of the tutorial showe how to move Domain Partition from one domain to another. We will demonstrate it on the example that origin domain will be running on premise and target domain will run in Oracle Cloud

![](images/part2.generic.overview.png)

Exporting and importing partitions let WLS system administrators easily move partitions from one domain to another, including the applications that are deployed to the partition. This feature is useful for replicating partitions across the domains and for moving domains from a development to a testing then production environment. 
Exporting a domain partition creates a partition backup and stores it in an archived format. You can export a domain partition from source domain with its entire configuration and data. With few configuration changes, you can then import the partition archive into another instance of multi-tenant WLS (the target domain). You might need to update any domain dependencies, such as targets and security realms, and optionally update other attributes in the partition configuration to make it valid. 

When a partition is exported from the source domain it is packaged in a partition archive. Included in the partition archive is:
- The partition configuration.
- Any resource group contained in the partitions.
- Any resource group templates referred to by those resource groups. 
- The contents of the file system, <partition-file-system>/config directory. 
- Optionally, application binaries and configuration for applications deployed to the partition. 
No application runtime state or application-specific runtime configuration is included in the partition archive. Examples of what would not be exported are JMS messages in queues, users in an embedded LDAP realm.

In this Lab, We are going to perform the below operations:
- Export a domain partition from a vanilla domain in development mode in on premise environment.
- Import the domain partition to a domain which is running in production mode and also in Java Cloud Service.

### Prerequisites ###

- Configured WebLogic Server 12cR2 domain with domain partition
- Running Java Cloud Service Instance with WebLogic Servert 12cR2 with corresponding Database Cloud Service Instance

### Steps ###

#### Export Domain Partition from on premise Domain1221 ####

Open in a browser console for on premise domain with WebLogic 12cR2. Login to that console if needed. Select **Domain Partitions** in a tree on left hand side and select checkbox for **Microcontainer1** and click on "Export" 

![](images/console.12cR2.domain.partition.export.png)

Check the box for **Include Application Bits**, and then enter **/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8/JCS** as Path and then click on OK

![](images/console.12cR2.domain.partition.export.details.png)

Go back to terminal, and verify the creation of `Microcontainer1.zip` and `Microcontainer1-attributes.json`. These files, we need while importing the partition in winsdemoWLS_domain in Java Cloud Service

**TBD OUTPUT IS MISSING HERE**

Eecute the following command:
  
    $ [oracle@localhost Desktop]$ cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8
    $ [oracle@localhost Lab8]$ mvn install -DLiftAndShift -Djcs.ip={PUBLIC IP OF JCS INSTANCE} -Ddbcs.ip={PUBLIC IP OF DBCS INSTANCE}
  
(The above maven command copies the required files to DBCS instance first, and populate the database with sample data. Here we used the SQL scripts for that. In general, you can unplug the local database, copies related files to DBCS instance and then plug the pluggable database to database in DBCS or you can export and import database. But to simplify the demo, we used the SQL scripts here. Then it copies the ZIP and JSON file of exported partition to /tmp folder of JCS instance, and provides sufficient permissions. It also creates the Virtual Target Microcontainer1-AdminServer-virtualTarget using a short WLST script, which we will be used by the imported partition.)

**TBD OUTPUT IS MISSING HERE**

#### Import  Domain Partition in Java Cloud Service ####

Now open or switch to the browser and access to [http://cloud.oracle.com](http://cloud.oracle.com) site. Click on Sign In

![](images/cloud.oracle.com.png)

Select the appropriate datacenter. ( US Commercial 2 (us2) )

**TBD MISSING SCREENSHOT **

Enter your identity domain and click on "Go".

![](images/cloud.identity.domain.png)

Enter your Oracle Public Cloud credentials and click on "Sign in".

![](images/cloud.username.password.png)

Now click on the hamburger icon next to the Java Services to open service console. 

**TBD MISSING SCREENSHOT **

Click on the winsdemoWLS instance for more details

![](images/jcs.service.details.png)

Click on the hamburger icon next to the instance navigation path. Select Open WebLogic Server Console to make the necessary correction after the import. Please note this change can be also automated by WLST scripts. The goal is here to demonstrate the Java Cloud Service capabilities.

![](images/jcs.console.open.png)

This will open a new tab and warn about missing certificate. Add this site as an exception to access the console. If you face any issue in opening the admin console in Virtual box, you can use the host machine’s browser, to open the Admin Console URL.

![](images/jcs.console.untrusted.png)

![](images/jcs.console.untrusted.exception.png)

Enter **weblogic/welcome1** as **Username/Password** and then click on "Login"

![](images/jcs.console.login.png)

Click on "Domain Partitions" and then click on "Lock & Edit"

![](images/jcs.console.domain.partitions.png)

Click on "Import", specify the file **/tmp/Microcontainer1.zip** in the **Path** field and click on "OK".

![](images/jcs.console.domain.partition.import.png)

Click on "Lock & Edit", and then select Services -> Data Sources -> PetstoreDB

![](images/jcs.console.lock.and.edit.png)

Click on the "Connection Pool" tab for PetstoreDB datasource, and modify the "URL" (jdbc:oracle:thin:@localhost:1521/pdborcl) with new value:
**jdbc:oracle:thin:winsdemo:1521/ PDB1.{DOMAIN_ID}.oraclecloud.internal** 
and then click on "Save". Where DOMAIN_ID is Identity Domain to which you logged in in oracle Cloud (look on page distributed to you by the Instructor)

![](images/jcs.jdbc.pool.url.change.png)

Click on "Activate Changes".

![](images/jcs.console.activate.changes.png)

Click on "Domain Partitions", go to "Control" tab, check the box near "Microcontainer1" partition and then click on "Start". On the confirmation screen click on "Yes"

![](images/jcs.console.domain.partition.select.png)

![](images/jcs.console.domain.partition.start.png)

Click on the refresh icon, once the partition is in RUNNING state, go to the browser and access the application [http://{PUBLIC_IP_OF_JCS_INSTANCE}/petstore/faces/catalog.jsp](http://{PUBLIC_IP_OF_JCS_INSTANCE}/petstore/faces/catalog.jsp)

![](images/petstore.on.jcs.png)


### Summary ###

1.	We took a domain Domain1036 created on WebLogic 10.3.6 Server, We used the Domain to Partition Conversion tool, to create archive and JSON file, which we later used for importing as partition in Domain1221 created on WebLogic 12.2.1 server. So in that way, Customer who has domain running in previous version of WebLogic Server, can be easily migrated to WebLogic Server 12.2.1 as partition.
2.	Exporting and Importing Partitions let WLS system administrators easily move partition from one domain to another, including the applications that are deployed to the partition. This features in useful for replicating partition across the domains and for moving domains from a development to a testing and then in production environment. Here we showed how you can replicate the partition running in development mode in on-premise to domain running in production mode in Java Cloud Service instance.  

