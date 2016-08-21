# ORACLE Public Cloud Service tutorial #
-----
## View Java Cloud Service log using SSH ##

### About this tutorial ###
You can access the services and resources that a service instance's VM provides by logging into the VM through SSH.
This tutorial demonstrates how to:
	
+ connect to Java Cloud Service's VM using ssh,
+ view the WebLogic server(s) log(s)

### Prerequisites ###

- [Deployed sample application on Java Cloud Service](https://github.com/oracle-weblogic/weblogic-innovation-seminars/blob/caf-12.2.1/cloud.demos/jcs.basics/deploy.to.jcs.md)
- Before you use the ssh utility to connect to a compute node, you need the following:
- The IP address of a compute node associated with a WebLogic deployment. The IP address is listed on Java Cloud Service Overview page.
- The SSH private key file that matches the public key associated with the deployment.

### Steps ###
Navigate to the Oracle Java Cloud Service Console. [Sign in](https://github.com/oracle-weblogic/weblogic-innovation-seminars/blob/caf-12.2.1/cloud.demos/jcs.basics/sign.in.to.oracle.cloud.md) to the My Services application at [cloud.oracle.com](http://cloud.oracle.com). On the dashboard open the Java Cloud Service Console.
![](images/create.jcs.00.png)

Click the service instance hosts sample application.
![](images/java.service.console.png)

Note the public IP address of the compute node hosting Java Cloud Service instance.
![](images/java.service.ip.address.png)

Open a terminal and change to folder `/u01/content/weblogic-innovation-seminars/cloud.demos`. 

    $ [oracle@localhost Desktop]$ cd /u01/content/weblogic-innovation-seminars/cloud.demos

Use `ssh` to connect remote VM. First parameter is the private key file location and the second is the remote user (default: **opc**) and remote system's address (the public IP address of instance). It should be the following if your private key's name is **privateKey** and can be found in the same folder where you execute `ssh`. Dont forget to replace the IP address according to your environment property:

    $ [oracle@localhost cloud.demos]$ ssh -i privateKey opc@140.86.6.145
    [opc@techco-wls-1 ~]$

Once the ssh connection established you need to switch user to oracle. It is necessary because opc has no right e.g. to read log files.

	[opc@techco-wls-1 ~]$ sudo su - oracle
	-bash-4.1$

The current location of the WebLogic runtime environment is `/u01/data/domains/<servicename>_domain`. Thus to print the log server of the Managed server should look like this. The name of the folders depend on the service instance name:

	tail -f /u01/data/domains/techco_domain/servers/techco_d_server_1/logs/techco_d_server_1.log
	####<Aug 21, 2016 9:55:32 AM UTC> <Info> <EJB> <techco-wls-1.compute-hujohni.oraclecloud.internal> <techco_d_server_1> <[ACTIVE] ExecuteThread: '22' for queue: 'weblogic.kernel.Default (self-tuning)'> <OracleSystemUser> <BEA1-1ABEC967515F8E9DF2CC> <9ecf3fd3-cde9-418e-b87b-a2557e76d81d-0000356d> <1471773332175> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-010227> <EJB exception occurred during invocation from home or business: oracle.wsm.policymanager.bean.ejb.impl.UsageTracker_oi3aq7_Intf generated exception: java.lang.SecurityException: WSM-02084 : Access denied. Permission "oracle.wsm.security.PolicyManagerPermission" is required to access the wsm policy manager "UsageTracker" method "recordUsage".> 
	####<Aug 21, 2016 9:55:38 AM UTC> <Info> <EJB> <techco-wls-1.compute-hujohni.oraclecloud.internal> <techco_d_server_1> <[ACTIVE] ExecuteThread: '33' for queue: 'weblogic.kernel.Default (self-tuning)'> <OracleSystemUser> <BEA1-1ABFC967515F8E9DF2CC> <9ecf3fd3-cde9-418e-b87b-a2557e76d81d-0000356e> <1471773338364> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-010227> <EJB exception occurred during invocation from home or business: oracle.wsm.policymanager.bean.ejb.impl.UsageTracker_oi3aq7_Intf generated exception: java.lang.SecurityException: WSM-02084 : Access denied. Permission "oracle.wsm.security.PolicyManagerPermission" is required to access the wsm policy manager "UsageTracker" method "recordUsage".> 
	####<Aug 21, 2016 9:56:02 AM UTC> <Info> <EJB> <techco-wls-1.compute-hujohni.oraclecloud.internal> <techco_d_server_1> <[ACTIVE] ExecuteThread: '37' for queue: 'weblogic.kernel.Default (self-tuning)'> <OracleSystemUser> <BEA1-1AC0C967515F8E9DF2CC> <9ecf3fd3-cde9-418e-b87b-a2557e76d81d-00003571> <1471773362182> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-010227> <EJB exception occurred during invocation from home or business: oracle.wsm.policymanager.bean.ejb.impl.UsageTracker_oi3aq7_Intf generated exception: java.lang.SecurityException: WSM-02084 : Access denied. Permission "oracle.wsm.security.PolicyManagerPermission" is required to access the wsm policy manager "UsageTracker" method "recordUsage".> 
	####<Aug 21, 2016 9:56:08 AM UTC> <Info> <EJB> <techco-wls-1.compute-hujohni.oraclecloud.internal> <techco_d_server_1> <[ACTIVE] ExecuteThread: '37' for queue: 'weblogic.kernel.Default (self-tuning)'> <OracleSystemUser> <BEA1-1AC1C967515F8E9DF2CC> <9ecf3fd3-cde9-418e-b87b-a2557e76d81d-00003572> <1471773368371> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-010227> <EJB exception occurred during invocation from home or business: oracle.wsm.policymanager.bean.ejb.impl.UsageTracker_oi3aq7_Intf generated exception: java.lang.SecurityException: WSM-02084 : Access denied. Permission "oracle.wsm.security.PolicyManagerPermission" is required to access the wsm policy manager "UsageTracker" method "recordUsage".> 
	####<Aug 21, 2016 9:56:32 AM UTC> <Info> <EJB> <techco-wls-1.compute-hujohni.oraclecloud.internal> <techco_d_server_1> <[ACTIVE] ExecuteThread: '34' for queue: 'weblogic.kernel.Default (self-tuning)'> <OracleSystemUser> <BEA1-1AC2C967515F8E9DF2CC> <9ecf3fd3-cde9-418e-b87b-a2557e76d81d-00003574> <1471773392190> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-010227> <EJB exception occurred during invocation from home or business: oracle.wsm.policymanager.bean.ejb.impl.UsageTracker_oi3aq7_Intf generated exception: java.lang.SecurityException: WSM-02084 : Access denied. Permission "oracle.wsm.security.PolicyManagerPermission" is required to access the wsm policy manager "UsageTracker" method "recordUsage".> 
	####<Aug 21, 2016 9:56:38 AM UTC> <Info> <EJB> <techco-wls-1.compute-hujohni.oraclecloud.internal> <techco_d_server_1> <[ACTIVE] ExecuteThread: '22' for queue: 'weblogic.kernel.Default (self-tuning)'> <OracleSystemUser> <BEA1-1AC3C967515F8E9DF2CC> <9ecf3fd3-cde9-418e-b87b-a2557e76d81d-00003575> <1471773398379> <[severity-value: 64] [rid: 0] [partition-id: 0] [partition-name: DOMAIN] > <BEA-010227> <EJB exception occurred during invocation from home or business: oracle.wsm.policymanager.bean.ejb.impl.UsageTracker_oi3aq7_Intf generated exception: java.lang.SecurityException: WSM-02084 : Access denied. Permission "oracle.wsm.security.PolicyManagerPermission" is required to access the wsm policy manager "UsageTracker" method "recordUsage".>

Obviously the log's content can differ. Once you checked the log file you can interrupt the `tail` process using Ctrl+C. Use exit command to log out from the ssh connection.

Note this is one way to check log file(s) related to Java Cloud Service. There are many other way to have the log content. You can use:

+ [WebLogic Administration Console](http://docs.oracle.com/cloud/latest/jcs_gs/JSCUG/GUID-6EBA48D9-F5FB-4AE1-9573-937ABE9EBD0C.htm#JSCUG3386)
+ Oracle Management Cloud - [Log Analytics](https://docs.oracle.com/cloud/latest/em_home/em_log_admin_tasks.htm)
+ More about Monitoring and Tuning of Java Cloud Service see the [documentation](http://docs.oracle.com/cloud/latest/jcs_gs/jcs_monitortune_12.1.3.htm).
