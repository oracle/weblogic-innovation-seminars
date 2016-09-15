# ORACLE Public Cloud Services tutorial #
-----
## Convert WebLogic 11g domain into the 12cR2 partition through DPCT tool ##

### About this tutorial ###

This part of the tutorial showe how to convert existing Weblogic Server 11g domain into the importable format of WebLogic Server 12cR2 Domain Partition and import that pertition into WebLogic Server 12cR2 domain

![](images/part1.generic.overview.png)

Inside VirtualBox VM we have already created Domain1036 in /u01/wins/wls1036/user_projects/domains/Domain1036. This domain contains Admin Server only, we created PetstoreDB as Datasource and Deployed JSF-2.0.war as shared library and deployed petstore.12.war as an application to the Admin Server. This is the **source domain**, which we are going to convert to the image of the WebLogic Domain Partition through DPCT tool. This process will create a Domain1036.zip and Domain1036-attributes.json file.

We have also created Domain1221 in /u01/wins/wls1221/user_projects/domains/Domain1221. This is empty domain, which contains Admin Server only. This will be our **target domain**, where we will import the previous domain Domain1036 as partition. 

### Prerequisites ###

- Configured WebLogic Server 11g domain with sample application
- Configured WebLogic Server 12cR2 domain
- Running Oracle Database with application data needed for sample application

### Steps ###

#### Starting the Admin Server in both domains ####

1. Open a terminal window and move to /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8

  cd /u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/Lab8

2. Copy prepared script that prepares the environment

  ./prepare_env.sh 

-	It starts the pluggable database **pdborcl**.
-	It creates the **petstore** user in pdborcl database, and populates the database with sample data.
-	It sets the **JAVA_HOME** to **/usr/java/jdk1.7.0_79/** and **MW_HOME** to **/u01/wins/wls1036/** then it starts the Admin Server in Domain1036.
-	Then it sets the **JAVA_HOME** to **/usr/java/latest/** (JDK 8) and **MW_HOME** to **/u01/wins/wls1221/** then it starts the Admin Server in Domain1221.

**TBD output of the script**

3. Go to browser and access the application URL on [http://localhost:6001/petstore/faces/catalog.jsp](http://localhost:6001/petstore/faces/catalog.jsp) . You can use the Bookmark.
![](images/call.petstore.on.11g.png)
We are using **Pet Store** application which was created by Sun engineers in 2009(!) to demonstrate JEE5 and other features. You can click on different Animal name like Cats, Dogs and Birds. You can also click on Seller and Search for verification of application Execution. Due to external API changes some of the functions do not work of the demo application. 

#### Running Domain to Partition Conversion Tool ####

We have put the Domain to Partition conversion tool in '/u01/dpct' folder.

Go back to terminal window and verify if **JAVA_HOME** variable is properly set.
	echo $JAVA_HOME 
The displayed value should be /usr/java/latest, which is JDK 8 required for DPCT tool to run.
Move to DPCT folder location
	cd /u01/dpct


