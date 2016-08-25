# ORACLE Public Cloud Services tutorial #
-----
## Using Eclipse IDE (Oracle Enterprise Pack for Eclipse) with Oracle Developer Cloud Service ##

### Introduction ###
You can access Oracle Developer Cloud Service projects from Integrated Development Environments (IDEs) such as Oracle Enterprise Pack for Eclipse (OEPE), Oracle JDeveloper, and NetBeans IDE. The Eclipse IDE and the Oracle Enterprise Pack for Eclipse (OEPE) includes integration for Oracle Developer Cloud Service, which conveniently exposes the most common Cloud development tasks from within the IDE.

Oracle Developer Cloud Service integration with the Eclipse IDE includes the following:

+ A dedicated Oracle Cloud view that displays Oracle Developer Cloud Service projects of which you are a member
+ Integration with Mylyn and the Oracle Developer Cloud Service Issues system
+ Source control system integration with the Oracle Developer Cloud Service Git repository

You can download the Eclipse IDE from [http://www.eclipse.org/](http://www.eclipse.org/) and OEPE from [http://www.oracle.com/technetwork/developer-tools/eclipse/downloads/index.html](http://www.oracle.com/technetwork/developer-tools/eclipse/downloads/index.html).

If you are using the Eclipse IDE, download and install the Oracle Cloud Tools plugin from the Eclipse IDE marketplace. In OEPE, the plugin is installed by default.

### About this tutorial ###
This tutorial demonstrates how to:
	
+ Setup Eclipse IDE (Oracle Enterprise Plugin for Eclipse)

### Prerequisites ###

+ [Create Oracle Developer Cloud Service project for SpringBoot application](https://github.com/oracle-weblogic/weblogic-innovation-seminars/blob/caf-12.2.1/cloud.demos/jcs.basics/create.devcs.project.springboot.md)
+ [Create continuous build integration using Oracle Developer Cloud Service and Oracle Application Container Cloud Service](https://github.com/oracle-weblogic/weblogic-innovation-seminars/blob/caf-12.2.1/cloud.demos/jcs.basics/devcs.accs.ci.md)
+ Eclipse IDE with Oracle Cloud Tools plugin or Oracle Enterprise Plugin for Eclipse installed.

### Steps ###

#### Configure Cloud Tools Plugin ####

Open OEPE and set (leave the default, `home/oracle/workspace`) workspace location. Close the Welcome page. To open the *Oracle Cloud* view from the Window menu, click *Show View* and then *Other*.

![](images/dcs/oepe.01.png)

From the list open *Oracle Cloud* and select *Oracle Cloud*.

![](images/dcs/oepe.02.png)

If you are connecting to Oracle Developer Cloud Service for the first time, click the Connect link.

![](images/dcs/oepe.03.png)

In the Oracle Cloud Service Connection dialog, enter the following:

+ **Data Center**: Select the Oracle Cloud data center.
+ **Identity Domain**: Enter the identity domain of Oracle Developer Cloud Service.
+ **Username** and **Password**: Enter the user name and password.
+ **Connection Name**: Enter a name for the service instance, if necessary. By default, the connection name is set to the identity domain name.

![](images/dcs/oepe.04.png)

Provide a master password to store securely your credentials, so in the future you don't need to enter every time when you open OEPE. Don't forget to note the master password.

![](images/dcs/oepe.05.png)

After your credentials have been validated, you are logged in to Oracle Developer Cloud Service and the Oracle Cloud view displays all projects that are assigned to you. Open the tree view myOracle Cloud(Cloud connection name) -> Developer -> springboot(DevCS project name) -> Code and there you can see the Git repository hosted on Oracle Developer Cloud Service.

![](images/dcs/oepe.06.png)

To clone the Git repository to your machine double click or right click -> Activate on the Git repository and clone will start.

![](images/dcs/oepe.07.png)

After the successful clone your local copy will be available in your workspace.

![](images/dcs/oepe.08.png)

To import a Maven based project right click on the Project Exporer area and will pop up the menu where you can select Import projects -> Import...

![](images/dcs/oepe.09.png)

Select Existing Maven Projects and click Next.

![](images/dcs/oepe.10.png)

Give the location of the cloned repository. If the workspace location is the default it should look like the following: `/home/oracle/workspace/springboot.git-9534/acc/springboot-sample`
Please note, that you need to give one of the subfolder of the local repository.
Click Finish.

![](images/dcs/oepe.11.png)

OEPE now starts to validate and build the project which can take few seconds. Finally you can see the imported project in the Project Explorer area.

![](images/dcs/oepe.12.png)

#### Change code and test using continuous integration ####

The project hosted on Oracle Developer Cloud Service has build job which ensures that new application build and deployment to Application Container Cloud Service happens when any developer oush code changes to Git repository.

Check the process modifying small piece on the application. First check the default page of the application. Open the tree view myOracleCloud -> Applications -> springboot-demo. Right click on springboot-demo and select Open In Browser menu item.

![](images/dcs/oepe.13.png)

New browser is opened and you should see the application's home page.

![](images/dcs/oepe.14.png)

Go back to OEPE and in the Project Explorer area find the *springbootdemo* project and open the **src/main/resources -> META-INF -> resources -> WEB-INF -> jsp -> welcome.jsp**

![](images/dcs/oepe.15.png)

Modify the following part:

	<body style="padding: 10px; border: 10px;">
		SpringBoot application demo. Current server time: ${time}

To what you would like to see on the page. For example:

	<body style="padding: 10px; border: 10px;">
		SpringBoot application demo <font color="red">MODIFIED IN OEPE.</font> Current server time: ${time}

Save the changes. To commit and push changes to Git right click on project and select Team -> Commit...

![](images/dcs/change.16.png) 

Git Staging view is displayed. First move the `welcome.jsp` to the Staged Changes area. You don't need to add Eclipse specific files created in the local Git repository. Type a commit message. Enter your name and Cloud username (email address). Click Commit and Push...

![](images/dcs/oepe.16.png)

In the Push dialog leave the default branch and click OK.

![](images/dcs/oepe.17.png)

Now change back to the browser and check the Build page in the Oracle Developer Cloud Service project. You should see that a new build (in our case: *springboot_build*) has been fired by the Git changes.

![](images/dcs/change.20.png)

Once the job is done change to the tab to Deploy and you can see that a new deployment has been started too. If you remember the Deployment was configured to redeploy every time when a new successful build artifact is ready.

![](images/dcs/change.21.png)

Now check the changes of the home page of the application. Open the tree view myOracleCloud -> Applications -> springboot-demo. Right click on springboot-demo and select Open In Browser menu item.

![](images/dcs/oepe.13.png)

New browser (tab) is opened and you should see the changes (red text) on the application's home page.

![](images/dcs/oepe.18.png)
