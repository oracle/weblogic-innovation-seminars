import time
import os

#if len(sys.argv) != 2:
#	print "Invalid arguments:: Usage configure.py <Middleware_home_path>" 
#	exit()

#var_mw_home=sys.argv[1]

var_mw_home=os.environ["MW_HOME"]
print 'Middlware Home: '+var_mw_home

var_wl_home=os.environ["WL_HOME"]
print 'Weblogic Home: '+var_wl_home

var_domain_name='appgrid_domain'
var_domain_dir=var_mw_home+'/user_projects/domains/'+var_domain_name
var_username='weblogic'
var_password='welcome1'

createDomain(var_wl_home+'/common/templates/domains/wls.jar',var_domain_dir,var_username,var_password)
print 'domain created'

readDomain(var_domain_dir)
print 'read domain'

cd('/')
cmo.setExalogicOptimizationsEnabled(false)
cmo.setClusterConstraintsEnabled(false)
cmo.setGuardianEnabled(false)
cmo.setAdministrationPortEnabled(false)
cmo.setConsoleEnabled(true)
cmo.setConsoleExtensionDirectory('console-ext')
cmo.setProductionModeEnabled(false)
cmo.setAdministrationProtocol('t3s')
cmo.setConfigBackupEnabled(false)
cmo.setConfigurationAuditType('none')
cmo.setInternalAppsDeployOnDemandEnabled(false)
cmo.setConsoleContextPath('console')
print 'set options'


cd('/Servers/AdminServer')
cmo.setListenPortEnabled(true)
cmo.setAdministrationPort(9002)
cmo.setListenPort(7001)
cmo.setWeblogicPluginEnabled(false)
cmo.setJavaCompiler('javac')
cmo.setStartupMode('RUNNING')
cmo.setVirtualMachineName(var_domain_name+'_AdminServer')
cmo.setClientCertProxyEnabled(false)

print 'updating domain'
updateDomain()

print 'connecting to node manager'
nmConnect(domainName=var_domain_name, username=var_username, password=var_password, nmType='plain', verbose='true', port=5556)

print 'starting admin server'
nmStart('AdminServer')
connect('weblogic','welcome1','t3://127.0.0.1:7001')

nmEnroll(domainDir=var_domain_dir,nmHome=var_wl_home+'/common/nodemanager')

edit()
startEdit()
###########################################################################
cd('/')
cmo.setExalogicOptimizationsEnabled(false)
cmo.setClusterConstraintsEnabled(false)
cmo.setGuardianEnabled(false)
cmo.setAdministrationPortEnabled(false)
cmo.setConsoleEnabled(true)
cmo.setConsoleExtensionDirectory('console-ext')
cmo.setProductionModeEnabled(false)
cmo.setAdministrationProtocol('t3s')
cmo.setConfigBackupEnabled(false)
cmo.setConfigurationAuditType('none')
cmo.setInternalAppsDeployOnDemandEnabled(false)
cmo.setConsoleContextPath('console')

cd('/JMX/appgrid_domain')
cmo.setManagementEJBEnabled(true)
cmo.setPlatformMBeanServerEnabled(false)
cmo.setPlatformMBeanServerUsed(true)
cmo.setCompatibilityMBeanServerEnabled(true)
cmo.setInvocationTimeoutSeconds(0)

cd('/DeploymentConfiguration/appgrid_domain')
cmo.setRemoteDeployerEJBEnabled(false)

cd('/AdminConsole/appgrid_domain')
cmo.setCookieName('appgrid_domain_ADMINCONSOLESESSION')
cmo.setSessionTimeout(3600)

###########################################################################
cd('/')
cmo.createCluster('appgrid-cluster-1')

cd('/Clusters/appgrid-cluster-1')
cmo.setClusterMessagingMode('unicast')

cd('/')
cmo.createServer('appgrid-ms-1')

cd('/Servers/appgrid-ms-1')
cmo.setListenAddress('127.0.0.1')
cmo.setListenPort(7101)
cmo.setCluster(getMBean('/Clusters/appgrid-cluster-1'))

cd('/')
cmo.createServer('appgrid-ms-2')

cd('/Servers/appgrid-ms-2')
cmo.setListenAddress('127.0.0.1')
cmo.setListenPort(7102)
cmo.setCluster(getMBean('/Clusters/appgrid-cluster-1'))

###########################################################################

cd('/')
cmo.createMachine('my-machine-1')

cd('/Machines/my-machine-1/NodeManager/my-machine-1')
cmo.setNMType('Plain')
cmo.setListenAddress('127.0.0.1')
cmo.setListenPort(5556)
cmo.setDebugEnabled(false)

cd('/')
cmo.createMachine('my-machine-2')

cd('/Machines/my-machine-2/NodeManager/my-machine-2')
cmo.setNMType('Plain')
cmo.setListenAddress('127.0.0.1')
cmo.setListenPort(5557)
cmo.setDebugEnabled(false)

###########################################################################
cd('/Servers/appgrid-ms-1')
cmo.setListenPortEnabled(true)
cmo.setJavaCompiler('javac')
cmo.setClientCertProxyEnabled(false)
cmo.setMachine(getMBean('/Machines/my-machine-1'))

cd('/Servers/appgrid-ms-1/SSL/appgrid-ms-1')
cmo.setEnabled(false)

cd('/Servers/appgrid-ms-1/ServerDiagnosticConfig/appgrid-ms-1')
cmo.setWLDFDiagnosticVolume('Low')

cd('/Servers/appgrid-ms-2')
cmo.setListenPortEnabled(true)
cmo.setJavaCompiler('javac')
cmo.setClientCertProxyEnabled(false)
cmo.setMachine(getMBean('/Machines/my-machine-2'))

cd('/Servers/appgrid-ms-2/SSL/appgrid-ms-2')
cmo.setEnabled(false)

cd('/Servers/appgrid-ms-2/ServerDiagnosticConfig/appgrid-ms-2')
cmo.setWLDFDiagnosticVolume('Low')

###########################################################################
cd('/')
cmo.createJDBCSystemResource('appgrid-cluster-ds-nonXA')

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA')
cmo.setName('appgrid-cluster-ds-nonXA')

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA/JDBCDataSourceParams/appgrid-cluster-ds-nonXA')
set('JNDINames',jarray.array([String('com/oracle/appgrid/example/jdbc/cluster-ds')], String))

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA/JDBCDriverParams/appgrid-cluster-ds-nonXA')
cmo.setUrl('jdbc:oracle:thin:@localhost:1521:XE')
cmo.setDriverName('oracle.jdbc.OracleDriver')
cmo.setPassword('weblogic')

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA/JDBCConnectionPoolParams/appgrid-cluster-ds-nonXA')
cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n\r\n')

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA/JDBCDriverParams/appgrid-cluster-ds-nonXA/Properties/appgrid-cluster-ds-nonXA')
cmo.createProperty('user')

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA/JDBCDriverParams/appgrid-cluster-ds-nonXA/Properties/appgrid-cluster-ds-nonXA/Properties/user')
cmo.setValue('weblogic')

cd('/JDBCSystemResources/appgrid-cluster-ds-nonXA/JDBCResource/appgrid-cluster-ds-nonXA/JDBCDataSourceParams/appgrid-cluster-ds-nonXA')
cmo.setGlobalTransactionsProtocol('None')

cd('/SystemResources/appgrid-cluster-ds-nonXA')
set('Targets',jarray.array([ObjectName('com.bea:Name=appgrid-cluster-1,Type=Cluster')], ObjectName))

###########################################################################
cd('/')
cmo.createJDBCStore('appgrid-jms-store-1')

cd('/JDBCStores/appgrid-jms-store-1')
cmo.setDataSource(getMBean('/SystemResources/appgrid-cluster-ds-nonXA'))
cmo.setPrefixName('JMSSTORE1')
set('Targets',jarray.array([ObjectName('com.bea:Name=appgrid-ms-1 (migratable),Type=MigratableTarget')], ObjectName))

cd('/')
cmo.createJDBCStore('appgrid-jms-store-2')

cd('/JDBCStores/appgrid-jms-store-2')
cmo.setDataSource(getMBean('/SystemResources/appgrid-cluster-ds-nonXA'))
cmo.setPrefixName('JMSSTORE2')
set('Targets',jarray.array([ObjectName('com.bea:Name=appgrid-ms-2 (migratable),Type=MigratableTarget')], ObjectName))

###########################################################################
cd('/')
cmo.createJMSServer('appgrid-jms-server-1')

cd('/Deployments/appgrid-jms-server-1')
cmo.setPersistentStore(getMBean('/JDBCStores/appgrid-jms-store-1'))
set('Targets',jarray.array([ObjectName('com.bea:Name=appgrid-ms-1 (migratable),Type=MigratableTarget')], ObjectName))

cd('/')
cmo.createJMSServer('appgrid-jms-server-2')

cd('/Deployments/appgrid-jms-server-2')
cmo.setPersistentStore(getMBean('/JDBCStores/appgrid-jms-store-2'))
set('Targets',jarray.array([ObjectName('com.bea:Name=appgrid-ms-2 (migratable),Type=MigratableTarget')], ObjectName))

###########################################################################
cd('/')
cmo.createJMSSystemResource('jms-module-base')

cd('/SystemResources/jms-module-base')
set('Targets',jarray.array([ObjectName('com.bea:Name=appgrid-cluster-1,Type=Cluster')], ObjectName))

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base')
cmo.createConnectionFactory('com/oracle/appgrid/example/jms/base/cf')

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/ConnectionFactories/com/oracle/appgrid/example/jms/base/cf')
cmo.setJNDIName('com/oracle/appgrid/example/jms/base/cf')

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/ConnectionFactories/com/oracle/appgrid/example/jms/base/cf/SecurityParams/com/oracle/appgrid/example/jms/base/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/ConnectionFactories/com/oracle/appgrid/example/jms/base/cf/ClientParams/com/oracle/appgrid/example/jms/base/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/ConnectionFactories/com/oracle/appgrid/example/jms/base/cf/TransactionParams/com/oracle/appgrid/example/jms/base/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/ConnectionFactories/com/oracle/appgrid/example/jms/base/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/ConnectionFactories/com/oracle/appgrid/example/jms/base/cf/LoadBalancingParams/com/oracle/appgrid/example/jms/base/cf')
cmo.setLoadBalancingEnabled(true)
cmo.setServerAffinityEnabled(false)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base')
cmo.createUniformDistributedQueue('com/oracle/appgrid/example/jms/base/queue')

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/UniformDistributedQueues/com/oracle/appgrid/example/jms/base/queue')
cmo.setJNDIName('com/oracle/appgrid/example/jms/base/queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base')
cmo.createUniformDistributedTopic('com/oracle/appgrid/example/jms/base/topic')

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/UniformDistributedTopics/com/oracle/appgrid/example/jms/base/topic')
cmo.setJNDIName('com/oracle/appgrid/example/jms/base/topic')
cmo.setForwardingPolicy('Replicated')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base')
cmo.createUniformDistributedTopic('com/oracle/appgrid/example/jms/base/clearscreen')
cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/UniformDistributedTopics/com/oracle/appgrid/example/jms/base/clearscreen')
cmo.setJNDIName('com/oracle/appgrid/example/jms/base/clearscreen')
cmo.setForwardingPolicy('Replicated')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base')
cmo.createUniformDistributedTopic('com/oracle/appgrid/example/jms/base/udt')
cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/UniformDistributedTopics/com/oracle/appgrid/example/jms/base/udt')
cmo.setJNDIName('com/oracle/appgrid/example/jms/base/udt')
cmo.setForwardingPolicy('Replicated')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base')
cmo.createUniformDistributedTopic('com/oracle/appgrid/example/jms/base/pdt')
cd('/JMSSystemResources/jms-module-base/JMSResource/jms-module-base/UniformDistributedTopics/com/oracle/appgrid/example/jms/base/pdt')
cmo.setJNDIName('com/oracle/appgrid/example/jms/base/pdt')
cmo.setForwardingPolicy('Partitioned')
cmo.setDefaultTargetingEnabled(true)

###########################################################################

save()
activate(block="true")
#shutdown('AdminServer')
nmKill('AdminServer')
nmServerStatus('AdminServer')
nmStart('AdminServer')
disconnect()

exit()
