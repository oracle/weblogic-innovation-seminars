# ORACLE Public Java Cloud Service tutorial #
-----
## Create Database Cloud Service instance using user interface ##

### About this tutorial ###
This tutorial demonstrates how to:
	
+ create Database Cloud Service using the user interface.

### Steps ###

[Sign in](sign.in.to.oracle.cloud.md) and navigate to the dashboard. On the dashboard click on the hamburger icon on the Databases tile. Select Open Service Console.
![](images/dashboard.2.dbcs.png)

This is the Database Cloud Service Console page. To create new instance click on Create Service button.
![](images/create.dbcs.00.png)

Select subscription type. Select the fully managed Oracle Database Cloud Service and the desired billing format. For more details about subscription types see the [documentation](https://docs.oracle.com/cloud/latest/dbcs_dbaas/CSDBI/GUID-F1E6807A-D283-4170-AB2B-9D43CD8DCD92.htm#CSDBI3395).
![](images/create.dbcs.01.png)

Select Software Release.
![](images/create.dbcs.02.png)

When creating a database deployment Oracle Database Cloud Service, the following Oracle Database editions are available:
![](images/create.dbcs.03.png)
Select Enterprise Edition.

The last input page is the Service Details page. The following parameters have to be provided:
	
+ Service Name: the name of the service instance e.g. techcoDB
+ Shape: number of OCPU and size of the RAM. Choose the smallest (default) one.
+ Timezone: set your timezone.
+ SSH Public Key: public key which will be uploaded to the VM during the creation. It allows to connect to the VM through ssh connection using the private key. To avoid additional task to create keypairs select Create a New Key option and download the newly generated keypair for later usage. Download the zip file to `/u01/content/weblogic-innovation-seminars/cloud.demos` folder. 
![](images/create.dbcs.04.pk.png)
+ Administrator Password: database instance's system password. Don't forget to note the provided password.
+ DB Name (SID): container database service identifier.
+ PDB Name: pluggable database service identifier.
+ Backup Destination: Leave default; Both Cloud and Local Storage
+ Cloud Storage Container: the name of the container for database instance. The format is the following: Storage-IDENTITYDOMAIN/CONTAINERNAME. The container don't need to be created in advance, because -see below- there is an option to create automatically.
+ Cloud Storage User Name and Password: the credentials for storage. Usually it is the same what was used to sign in to Oracle Cloud Services.
+ Create Cloud Storage Containers: if the container defined above does not exist then check in.
+ Character Set: The database character set for the database. Leave default.
+ National Character Set: The national character set is used for data stored in SQL NCHAR data types. Leave default.
![](images/create.dbcs.04.png)
For more details about parameters see the [documentation](https://docs.oracle.com/cloud/latest/dbcs_dbaas/CSDBI/GUID-D4A35763-53ED-4FBB-97BF-0366F21B05E0.htm#CSDBI3401). Click on Next.

The final page is the summary about the configuration before submit the instance creation request. Click on Create to start the provisioning of the new service instance.
![](images/create.dbcs.05.png)

When the request has been accepted the Database Service Console page appears and shows the new instance. The instance now is in Maintenance (Progress) mode. Click on In Progress to get more information about the status.
![](images/create.dbcs.06.png)

Wait until the service provisioning will be complete.


