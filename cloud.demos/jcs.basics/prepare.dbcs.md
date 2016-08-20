# ORACLE Public s Cloud Service tutorial #
-----
## Prepare Database Cloud Service ##

### About this tutorial ###
This tutorial demonstrates how to:
	
+ connect to Database Cloud Service's VM using ssh,
+ execute SQL script to prepare database service for application

### Prerequisites ###

Before you use the ssh utility to connect to a compute node, you need the following:

- The IP address of the compute node. The IP address of a compute node associated with a database deployment on Oracle Database Cloud Service is listed on the Oracle Database Cloud Service Overview page.

- The SSH private key file that matches the public key associated with the deployment.

### Steps ###
[Sign in](https://github.com/oracle-weblogic/weblogic-innovation-seminars/blob/caf-12.2.1/cloud.demos/jcs.basics/sign.in.to.oracle.cloud.md) to cloud.oracle.com. On the dashboard open the Database Cloud Service Console.
![](images/dashboard.2.dbcs.png)

Click on the service instance what will be the database for the sample application.
![](images/prepare.dbcs.01.png)

Note the public IP address of the compute node hosting database service instance.
![](images/prepare.dbcs.02.png)

Open a terminal and change to folder `/u01/content/weblogic-innovation-seminars/cloud.demos`. Unzip the file which contains the private and public keys. Most likely its (default) name is sshkeybundle.zip.

    $ [oracle@localhost Desktop]$ cd /u01/content/weblogic-innovation-seminars/cloud.demos
    $ [oracle@localhost cloud.demos]$ unzip sshkeybundle.zip
    Archive:  sshkeybundle.zip
      inflating: privateKey              
      inflating: publicKey     
Before using privateKey it is required to change the mode of the file to rw by owner only.

    $ [oracle@localhost cloud.demos]$  chmod 600 privateKey

Now change to `/u01/content/weblogic-innovation-seminars/cloud.demos/techco.sample.app` folder. In the same terminal change the directory.

    $ [oracle@localhost cloud.demos]$  cd techco.sample.app
You will need to create an OE user in your DBCS PDB. This user will need rights to create and drop schema. Once the user is created the script create and populate the tables.

Use the init-dbcs-pdb.sh script to automate the creation of the user, tables and data. Usage is:

    usage: ./init-dbcs-pdb.sh <db user> <db password> <ssh key file> <db server ip> [<PDB name>]
`<db user>` should be `system`.  `<ssh key file>` the privateKey what is extracted to `/u01/content/weblogic-innovation-seminars/cloud.demos` folder.  `<db server ip>` the public IP address was determined in the steps above. `<PDB name>` is likely 'PDB1' and the script will default to this value if it is not given however you may override.

    [oracle@localhost techco.sample.app]$ ./init-dbcs-pdb.sh system syspassword ../privateKey 140.86.6.100 PDB1
    SQL*Plus: Release 12.1.0.2.0 Production on Fri Aug 19 14:02:30 2016

    Copyright (c) 1982, 2014, Oracle.  All rights reserved.

    Last Successful login time: Fri Aug 19 2016 13:28:58 +02:00

    Connected to:
    Oracle Database 12c Enterprise Edition Release 12.1.0.2.0 - 64bit Production

    SQL> DROP USER oe
    *
    ERROR at line 1:
    ORA-01922: CASCADE must be specified to drop 'OE'
    ...
	...
	...
	SQL> 
	1 row created.

	SQL> SQL> 
	Commit complete.

	SQL> Disconnected from Oracle Database 12c Enterprise Edition Release 12.1.0.2.0 - 64bit Production	

Now the database service is ready for the sample application.