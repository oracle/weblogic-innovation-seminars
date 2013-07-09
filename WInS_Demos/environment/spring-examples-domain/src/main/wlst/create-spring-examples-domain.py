import time

loadProperties('environment.properties')

########################################################################################################################

domain_Name = 'spring-examples-domain'
machine_ListenAddress = 'wins-vbox.localdomain'
jmsServer_BaseName = 'jms-server'
cluster_Name = 'cluster-1'
machine_Name = 'wins-vbox.localdomain'

wins_demos_home = '/u01/content/weblogic-innovation-seminars/WInS_Demos'

########################################################################################################################

datasource_JndiName = 'jdbc.ds.weblogic_examples'
datasource_GlobalTransactions = 'None'
datasource_JdbcDriver = 'oracle.jdbc.OracleDriver'
datasource_User = 'spring_examples_domain'
datasource_Password = 'spring_examples_domain'

########################################################################################################################

COHERENCE_LIB = COHERENCE_HOME + '/lib/coherence.jar'
COHERENCE_WEB_LIB = COHERENCE_HOME + '/lib/coherence-web-spi.war'
ACTIVE_CACHE_LIB = WL_HOME + '/common/deployable-libraries/active-cache-1.0.jar'
WLS_SPRING_LIB = WL_HOME + '/server/lib/weblogic-spring.jar'
TOPLINK_GRID_LIB = MW_HOME + '/oracle_common/modules/oracle.toplink_12.1.2/toplink-grid.jar'

########################################################################################################################

adminServer_ListenAddress = 'wins-vbox.localdomain'
adminServer_ListenPort = 20001
adminServer_AdministrationPort = 20200
adminServer_Username = 'weblogic'
adminServer_Password = 'welcome1'
adminServer_URL = 't3://' + adminServer_ListenAddress + ':' + str(adminServer_ListenPort)
adminServer_StartupArgs = '-Xms=256m -Xmx=256m'\
                          ' -Dweblogic.nodemanager.sslHostNameVerificationEnabled=false'\
                          ' -Dweblogic.security.SSL.ignoreHostnameVerify=true'\
                          ' -Dweblogic.security.SSL.ignoreHostnameVerification=true'\
                          ' -Dweblogic.security.TrustKeyStore=DemoTrust'

########################################################################################################################

managedServer_Count = 2
managedServer_BaseName = 'ms'
managedServer_BasePort = '2010'
managedServer_BaseAdminPort = '2020'
#managedServer_StartupArgs = '-XX:FlightRecorderOptions=defaultrecording=true '\
#                            ' -Xms256m -Xmx512m '
managedServer_StartupArgs = '-XX:+UnlockCommercialFeatures -XX:+FlightRecorder '\
                            ' -Xms256m -Xmx512m '
########################################################################################################################

cohCluster_Name = 'coherence-cluster-1'
cohCluster_ListenAddress = 'wins-vbox.localdomain'
cohCluster_ListenPort = 8088
cohCluster_TTL = 0

cohServer_Count = 2
cohServer_Classpath = MW_HOME + '/oracle_common/modules/oracle.toplink_12.1.2/toplink-grid.jar:'\
                      + MW_HOME + '/oracle_common/modules/oracle.toplink_12.1.2/eclipselink.jar:'\
                      + MW_HOME + '/coherence/lib/coherence.jar:'\
                      + MW_HOME + '/oracle_common/modules/javax.management.j2ee_1.1.0.0.jar:'\
                      + MW_HOME + '/oracle_common/modules/oracle.jdbc_11.2.0/ojdbc6.jar:'\
                      + MW_HOME + '/coherence/lib/coherence-web-spi.war:'\
                      + WL_HOME + '/modules/features/weblogic.server.modules.coherence.server_12.1.2.0.jar '

cohServer_StartupArgs = '-Dtangosol.coherence.management.remote=true'\
                        + ' -Dtangosol.coherence.management=all'\
                        + ' -Dtangosol.coherence.distributed.localstorage=true'\
                        + ' -Dtangosol.coherence.session.localstorage=true'\
                        + ' -Dtangosol.coherence.cacheconfig=' + wins_demos_home + '/coherence-examples/session-cache-config.xml'

########################################################################################################################
########################################################################################################################
########################################################################################################################
########################################################################################################################
########################################################################################################################
########################################################################################################################
########################################################################################################################

def createClusterDataSource(jndiNames, driver, globalTX, url, user, passwd, target):
  print '### createClusterDataSource #################################################################################'

  dsName = jndiNames[0]

  print 'Creating Physical DataSource ' + dsName

  cd('/')
  jdbcSystemResource = create(dsName, "JDBCSystemResource")

  cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
  dataSourceParams = create('dataSourceParams', 'JDBCDataSourceParams')
  dataSourceParams.setGlobalTransactionsProtocol(globalTX)
  cd('JDBCDataSourceParams/NO_NAME_0')
  set('JNDINames', jarray.array(jndiNames, String))

  cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
  connPoolParams = create('connPoolParams', 'JDBCConnectionPoolParams')
  connPoolParams.setMaxCapacity(25)
  connPoolParams.setInitialCapacity(15)
  connPoolParams.setCapacityIncrement(2)
  connPoolParams.setTestConnectionsOnReserve(true)
  connPoolParams.setTestTableName('SQL SELECT 1 FROM DUAL')

  cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
  driverParams = create('driverParams', 'JDBCDriverParams')
  driverParams.setUrl(url)
  driverParams.setDriverName(driver)
  driverParams.setPasswordEncrypted(passwd)
  cd('JDBCDriverParams/NO_NAME_0')

  create(dsName, 'Properties')
  cd('Properties/NO_NAME_0')

  create('user', 'Property')
  cd('Property/user')
  cmo.setValue(user)

  cd('/JDBCSystemResource/' + dsName)
  jdbcSystemResource.setTargets(jarray.array([target], weblogic.management.configuration.TargetMBean))

  print dsName + ' successfully created.'
  return jdbcSystemResource

########################################################################################################################

def createPhysicalDataSource(jndiNames, driver, globalTX, url, user, passwd, minSize, maxSize, target):
  print '### createPhysicalDataSource #################################################################################'

  dsName = jndiNames[0]

  print 'Creating Physical DataSource ' + dsName

  cd('/')
  jdbcSystemResource = create(dsName, "JDBCSystemResource")

  cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
  dataSourceParams = create('dataSourceParams', 'JDBCDataSourceParams')
  dataSourceParams.setGlobalTransactionsProtocol(globalTX)
  cd('JDBCDataSourceParams/NO_NAME_0')
  set('JNDINames', jarray.array(jndiNames, String))

  cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
  connPoolParams = create('connPoolParams', 'JDBCConnectionPoolParams')
  connPoolParams.setInitialCapacity(minSize)
  connPoolParams.setMaxCapacity(maxSize)
  connPoolParams.setCapacityIncrement(1)
  connPoolParams.setTestConnectionsOnReserve(true)
  connPoolParams.setTestTableName('SQL SELECT 1 FROM DUAL')

  cd('/JDBCSystemResource/' + dsName + '/JdbcResource/' + dsName)
  driverParams = create('driverParams', 'JDBCDriverParams')
  driverParams.setUrl(url)
  driverParams.setDriverName(driver)
  driverParams.setPasswordEncrypted(passwd)
  cd('JDBCDriverParams/NO_NAME_0')

  create(dsName, 'Properties')
  cd('Properties/NO_NAME_0')

  create('user', 'Property')
  cd('Property/user')
  cmo.setValue(user)

  cd('/JDBCSystemResource/' + dsName)
  jdbcSystemResource.setTargets(jarray.array([target], weblogic.management.configuration.TargetMBean))

  print dsName + ' successfully created.'
  return jdbcSystemResource

########################################################################################################################

def createBaseJMSResources(moduleName, clusterTarget, jmsServerTargets):
  print '### createBaseJMSResources ###################################################################################'

  cd('/')
  jmsMySystemResource = create(moduleName, 'JMSSystemResource')
  jmsMySystemResource.setTargets(jarray.array([clusterTarget], weblogic.management.configuration.TargetMBean))

  cd('/JMSSystemResources/' + moduleName)
  subdeployment = create('cluster-subdeployment', 'SubDeployment')
  subdeployment.setTargets(jarray.array(jmsServerTargets, weblogic.management.configuration.TargetMBean))

  cd('/JMSSystemResource/' + moduleName + '/JmsResource/NO_NAME_0')

  cf_name = 'com.oracle.example.jms.base.cf'

  myCF = create(cf_name, 'ConnectionFactory')
  myCF.setJNDIName(cf_name)
  myCF.setDefaultTargetingEnabled(true)

  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0/ConnectionFactories/' + cf_name)
  lbParams = create(cf_name, 'LoadBalancingParams')
  lbParams.setLoadBalancingEnabled(true)
  lbParams.setServerAffinityEnabled(false)

  txParams = create(cf_name, 'TransactionParams')
  txParams.setXAConnectionFactoryEnabled(true)

  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0')
  queue = create('com.oracle.example.jms.base.queue', 'UniformDistributedQueue')
  queue.setJNDIName('com.oracle.example.jms.base.queue')
  queue.setDefaultTargetingEnabled(false)
  queue.setSubDeploymentName('cluster-subdeployment')

  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0')
  topic_name = 'com.oracle.example.jms.base.topic'
  topic = create(topic_name, 'UniformDistributedTopic')
  topic.setJNDIName(topic_name)
  topic.setForwardingPolicy('Replicated')
  topic.setDefaultTargetingEnabled(false)
  topic.setSubDeploymentName('cluster-subdeployment')

  topic_name = 'com.oracle.example.jms.base.clearscreen'
  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0')
  topic = create(topic_name, 'UniformDistributedTopic')
  topic.setJNDIName(topic_name)
  topic.setForwardingPolicy('Replicated')
  topic.setDefaultTargetingEnabled(false)
  topic.setSubDeploymentName('cluster-subdeployment')

  topic_name = 'com.oracle.example.jms.base.replicated-topic'
  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0')
  topic = create(topic_name, 'UniformDistributedTopic')
  topic.setJNDIName(topic_name)
  topic.setForwardingPolicy('Replicated')
  topic.setDefaultTargetingEnabled(false)
  topic.setSubDeploymentName('cluster-subdeployment')

  topic_name = 'com.oracle.example.jms.base.partitioned-topic'
  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0')
  topic = create(topic_name, 'UniformDistributedTopic')
  topic.setJNDIName(topic_name)
  topic.setForwardingPolicy('Partitioned')
  topic.setDefaultTargetingEnabled(false)
  topic.setSubDeploymentName('cluster-subdeployment')


########################################################################################################################

def getJMSServerName(n):
  jms_server_name = jmsServer_BaseName + '-' + str(n)
  return jms_server_name


########################################################################################################################

def getManagedServerListenPort(n):
  managedServer_ListenPort = int(str(managedServer_BasePort) + str(n))
  return managedServer_ListenPort

########################################################################################################################

def getManagedServerName(n):
  managedServerName = managedServer_BaseName + '-' + str(n)
  return managedServerName

########################################################################################################################

def getManagedServerAdminPort(n):
  managedServer_AdminPort = int(str(managedServer_BaseAdminPort) + str(n))
  return managedServer_AdminPort

########################################################################################################################

def createSpringJMSTempResources(moduleName, clusterTarget, jmsServerTargets):
  print '### createSpringJMSTempResources #############################################################################'

  cd('/')
  jmsMySystemResource = create(moduleName, 'JMSSystemResource')
  jmsMySystemResource.setTargets(jarray.array([clusterTarget], weblogic.management.configuration.TargetMBean))

  cd('/JMSSystemResources/' + moduleName)
  subdeployment = create('cluster-subdeployment', 'SubDeployment')
  subdeployment.setTargets(jarray.array(jmsServerTargets, weblogic.management.configuration.TargetMBean))

  cd('/JMSSystemResource/' + moduleName + '/JmsResource/NO_NAME_0')

  ######## Connection Factory
  cf_name = 'com.oracle.example.jms.spring.cf'
  myCF = create(cf_name, 'ConnectionFactory')
  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0/ConnectionFactories/' + cf_name)

  myCF.setJNDIName(cf_name)
  myCF.setDefaultTargetingEnabled(true)

  #### Queue
  cd('/JMSSystemResources/' + moduleName + '/JmsResource/NO_NAME_0')
  queue_name = 'com.oracle.example.jms.spring.queue'

  queue = create(queue_name, 'UniformDistributedQueue')
  queue.setJNDIName(queue_name)
  queue.setDefaultTargetingEnabled(false)
  queue.setSubDeploymentName('cluster-subdeployment')


########################################################################################################################

def createMachine(machine_name, nodemanager_type, listen_address, listen_port):
  print '### createMachine ############################################################################################'

  cd('/')
  machine = create(machine_name, 'Machine')

  cd('/Machines/' + machine_name + '/')
  nodeManager = create(machine_name, 'NodeManager')

  #cd('/Machines/' + machine_name + '/NodeManager/' + machine_name)
  nodeManager.setNMType(nodemanager_type)
  nodeManager.setListenAddress(listen_address)
  nodeManager.setListenPort(listen_port)

  return machine

########################################################################################################################

def createCoherenceCluster_online(coh_cluster_name, coh_listen_address, coh_listen_port, targets_array, coh_ttl):
  print '@@@ createCoherenceCluster_online @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

  cd('/')
  cmo.createCoherenceClusterSystemResource(coh_cluster_name)

  cd(
    '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name)
  cmo.setUnicastListenAddress(coh_listen_address)
  cmo.setUnicastListenPort(coh_listen_port)
  cmo.setUnicastPortAutoAdjust(true)

  cmo.setMulticastListenAddress('231.1.1.1')
  cmo.setMulticastListenPort(7777)

  cd('/CoherenceClusterSystemResources/' + coh_cluster_name)
  set('Targets', targets_array)

  cd(
    '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name + '/CoherenceClusterWellKnownAddresses/' + coh_cluster_name)
  cmo.createCoherenceClusterWellKnownAddress('WKA-0')

  cd(
    '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name + '/CoherenceClusterWellKnownAddresses/' + coh_cluster_name + '/CoherenceClusterWellKnownAddresses/WKA-0')
  cmo.setListenPort(coh_listen_port)
  cmo.setListenAddress(coh_listen_address)

  cd(
    '/CoherenceClusterSystemResources/' + coh_cluster_name + '/CoherenceClusterResource/' + coh_cluster_name + '/CoherenceClusterParams/' + coh_cluster_name)
  cmo.setTimeToLive(coh_ttl)

  print 'Created Cluster name=[' + coh_cluster_name + ']'

########################################################################################################################

def createCoherenceServers_online(coh_cluster_name,
                                  coh_listen_address,
                                  coh_listen_port,
                                  coh_server_args,
                                  coh_server_count,
                                  coh_server_cp,
                                  machine_name):
  print '@@@ createCoherenceServers_online @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

  for n in range(1, int(coh_server_count) + 1):
    coh_server_name = 'coh-' + str(n)
    createCoherenceServer(coh_server_name, coh_cluster_name, machine_name, coh_listen_address, coh_listen_port,
                          coh_server_args,
                          coh_server_cp)

########################################################################################################################


def createCoherenceServer(coh_server_name, cluster_name, machine_name, coh_listen_address, coh_listen_port,
                          coh_server_args, coh_server_cp):
  cd('/')
  cmo.createCoherenceServer(coh_server_name)

  cd('/CoherenceServers/' + coh_server_name)
  cmo.setMachine(getMBean('/Machines/' + machine_name))
  cmo.setCoherenceClusterSystemResource(getMBean('/CoherenceClusterSystemResources/' + cluster_name))
  cmo.setUnicastListenAddress(coh_listen_address)
  cmo.setUnicastListenPort(coh_listen_port)
  cmo.setUnicastPortAutoAdjust(true)

  cd('/CoherenceServers/' + coh_server_name + '/CoherenceServerStart/' + coh_server_name)
  cmo.setArguments(coh_server_args)
  cmo.setClassPath(coh_server_cp)

  print 'Created Coherence server name=[' + coh_server_name + ']'

########################################################################################################################

def createSpringWLDFModule_online():
  print '@@@ createSpringWLDFModule_online @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'
  cd('/')
  cmo.createWLDFSystemResource('SpringMBeanWLDFModule')

  cd('/WLDFSystemResources/SpringMBeanWLDFModule')
  cmo.setDescription('Spring_WLDF_Module')
  #set('Targets',jarray.array([ObjectName('com.bea:Name=cluster-1,Type=Cluster')], ObjectName))

  cd(
    '/WLDFSystemResources/SpringMBeanWLDFModule/WLDFResource/SpringMBeanWLDFModule/WatchNotification/SpringMBeanWLDFModule')
  cmo.createWatch('SpringCounterMBeanWatch')

  cd(
    '/WLDFSystemResources/SpringMBeanWLDFModule/WLDFResource/SpringMBeanWLDFModule/WatchNotification/SpringMBeanWLDFModule/Watches/SpringCounterMBeanWatch')
  cmo.setRuleType('Harvester')
  cmo.setEnabled(true)
  cmo.setRuleExpression(
    '(${ServerRuntime//[com.oracle.weblogic.examples.spring.counter.CounterBeanMBean]counter.bean:Name=CounterBean//Value} = \'\')')
  cmo.setAlarmType(None)

  cd(
    '/WLDFSystemResources/SpringMBeanWLDFModule/WLDFResource/SpringMBeanWLDFModule/WatchNotification/SpringMBeanWLDFModule')
  cmo.createJMSNotification('SpringCounterJMSNotification')

  cd(
    '/WLDFSystemResources/SpringMBeanWLDFModule/WLDFResource/SpringMBeanWLDFModule/WatchNotification/SpringMBeanWLDFModule/JMSNotifications/SpringCounterJMSNotification')
  cmo.setEnabled(true)
  cmo.setDestinationJNDIName('com.oracle.example.jms.wldf.notification')
  cmo.setConnectionFactoryJNDIName('com.oracle.example.jms.wldf.cf')

########################################################################################################################

def createJMSModules(clusterMBean, jmsServerMBeans):
  print '### Creating JMS Modules #####################################################################################'
  createBaseJMSResources('jms-module-base', clusterMBean, jmsServerMBeans)
  createSpringJMSTempResources('jms-module-temp', clusterMBean, jmsServerMBeans)

########################################################################################################################

def deploySharedLibraries():
  print '@@@ Deploying Shared Libraries @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

  deploySharedLibrary('coherence', COHERENCE_LIB)
  deploySharedLibrary('coherence-web-spi', COHERENCE_WEB_LIB)
  deploySharedLibrary('active-cache', ACTIVE_CACHE_LIB)
  deploySharedLibrary('weblogic-spring', WLS_SPRING_LIB)
  deploySharedLibrary('toplink-grid', TOPLINK_GRID_LIB)

########################################################################################################################

def deploySharedLibrary(appName, appPath):
  progress = deploy(appName=appName, path=appPath, targets=cluster_Name, libraryModule='true')
  progress.printStatus()

########################################################################################################################

def configureDomain_online():
  cd('/JTA/' + domain_Name)
  cmo.setTimeoutSeconds(300)

  cd('/SelfTuning/' + domain_Name)
  cmo.createWorkManager('wm/CoherenceWorkManager')

  cd('/SelfTuning/' + domain_Name + '/WorkManagers/wm/CoherenceWorkManager')
  set('Targets', jarray.array(
    [ObjectName('com.bea:Name=AdminServer,Type=Server'), ObjectName('com.bea:Name=cluster-1,Type=Cluster')],
                                                                                                           ObjectName))


########################################################################################################################

def configureManagedServersOnline():
  print '@@@ Configuring Managed Servers Online @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

  for n in range(1, int(managedServer_Count) + 1):
    managedServer_Name = getManagedServerName(n)
    cd('/Servers/' + managedServer_Name + '/')
    cmo.setHealthCheckIntervalSeconds(60)
    cmo.setStuckThreadMaxTime(180)

    cd('/Servers/' + managedServer_Name + '/OverloadProtection/' + managedServer_Name)
    cmo.createServerFailureTrigger()

    cd(
      '/Servers/' + managedServer_Name + '/OverloadProtection/' + managedServer_Name + '/ServerFailureTrigger/' + managedServer_Name)
    cmo.setMaxStuckThreadTime(180)
    cmo.setStuckThreadCount(0)

########################################################################################################################

var_domain_dir = USER_PROJECTS + '/domains/' + domain_Name
print 'Creating domain in path=' + var_domain_dir

try:
  createDomain(WL_HOME + '/common/templates/wls/wls.jar', var_domain_dir, adminServer_Username,
               adminServer_Password)
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

  cd('/Servers/AdminServer')
  cmo.setListenPortEnabled(true)
  cmo.setAdministrationPort(int(adminServer_AdministrationPort))
  cmo.setListenPort(int(adminServer_ListenPort))
  cmo.setListenAddress(adminServer_ListenAddress)
  cmo.setWeblogicPluginEnabled(false)
  cmo.setJavaCompiler('javac')
  cmo.setStartupMode('RUNNING')
  cmo.setVirtualMachineName(domain_Name + '_AdminServer')
  cmo.setClientCertProxyEnabled(false)

  create('AdminServer', 'ServerStart')

  cd('/Servers/AdminServer/ServerStart/AdminServer')

  cmo.setJavaHome(JAVA_HOME)
  cmo.setArguments(adminServer_StartupArgs)

except:
  print 'Unable to create domain!'
  dumpStack()
  exit('1')

try:
  print 'updating domain'
  updateDomain()
except:
  print 'Unable to update domain'
  dumpStack()
  exit('1')

machine = createMachine(machine_Name, 'Plain', machine_ListenAddress, 5556)

cd('/')
clusterMBean = create(cluster_Name, 'Cluster')

try:
  jdbcSystemResource = createClusterDataSource(['com.oracle.weblogic.demo.jdbc.cluster-ds'],
                                                                                           datasource_JdbcDriver,
                                                                                           datasource_GlobalTransactions
                                                                                           ,
                                                                                           datasource_jdbc_url,
                                                                                           datasource_User,
                                                                                           datasource_Password,
                                                                                           clusterMBean)

  # changed from consensus by JAW
  clusterMBean.setMigrationBasis('database')
  clusterMBean.setDataSourceForAutomaticMigration(jdbcSystemResource)
  ####### Create Managed Servers

  print 'Creating ' + str(managedServer_Count) + ' Managed Servers...'

  jmsServerMBeans = []
  managedServerMBeans = []
  migratableTargetMBeans = []

  for n in range(1, int(managedServer_Count) + 1):
    managedServer_Name = managedServer_BaseName + '-' + str(n)
    managedServer_ListenPort = int(str(managedServer_BasePort) + str(n))
    managedServer_AdminPort = int(str(managedServer_BaseAdminPort) + str(n))
    managedServer_ListenAddress = machine_ListenAddress
    migratableTargetName = managedServer_Name + ' (migratable)'

    print 'Creating Server Name=[' + managedServer_Name + '] with Listen Port: ' + str(managedServer_ListenPort)
    cd('/')
    managedServer = create(managedServer_Name, 'Server')
    managedServer.setListenPort(managedServer_ListenPort)
    managedServer.setListenAddress(managedServer_ListenAddress)
    managedServer.setAdministrationPort(managedServer_AdminPort)
    managedServer.setCluster(clusterMBean)
    managedServer.setMachine(machine)
    managedServer.setAutoRestart(false)
    managedServerMBeans.append(managedServer)

    cd('/Servers/' + managedServer_Name)
    serverStart = create(managedServer_Name, 'ServerStart')
    serverStart.setJavaHome(JAVA_HOME)
    serverStart.setArguments(managedServer_StartupArgs)

    cd('/')

    print 'Creating MigratableTarget name=[' + migratableTargetName + ']'
    migratableTarget = create(migratableTargetName, 'MigratableTarget')
    migratableTarget.setUserPreferredServer(managedServer)
    migratableTarget.setMigrationPolicy('failure-recovery')
    migratableTargetMBeans.append(migratableTarget)

  for migratableTarget in migratableTargetMBeans:
    migratableTarget.setConstrainedCandidateServers(managedServerMBeans)
    cd('/')

  # SPLIT INTO DIFFERENT SCOPES BECAUSE IT WASNT WORKING IN A SINGLE SCOPE!
  for n in range(1, int(managedServer_Count) + 1):
    jmsServerName = jmsServer_BaseName + '-' + str(n)
    jdbcStoreName = jmsServerName + '-jdbcStore'
    managedServer_Name = managedServerMBeans[n - 1].getName()

    print 'Managed Server Name: ' + managedServer_Name

    jmsServerTargets = jarray.array([migratableTargetMBeans[n - 1]], weblogic.management.configuration.TargetMBean)

    print 'Creating JMS JDBC Store [' + jdbcStoreName + ']'
    cd('/')
    jdbcStore = create(jdbcStoreName, 'JDBCStore')
    jdbcStore.setDataSource(jdbcSystemResource)
    jdbcStore.setPrefixName('JMSSTORE' + str(n))
    jdbcStore.setTargets(jmsServerTargets)

    print 'Creating JMS Server Name=[' + jmsServerName + ']'
    cd('/')
    jmsServer = create(jmsServerName, 'JMSServer')
    jmsServerMBeans.append(jmsServer)
    jmsServer.setPersistentStore(jdbcStore)
    jmsServer.setTargets(jmsServerTargets)

  createJMSModules(clusterMBean, jmsServerMBeans)

except:
  dumpStack()
  exit('1')

try:
  cd('/')

  createPhysicalDataSource([datasource_JndiName],
                                                datasource_JdbcDriver,
                                                datasource_GlobalTransactions,
                                                datasource_jdbc_url,
                                                datasource_User,
                                                datasource_Password,
                                                5, 5,
                                                clusterMBean)
except:
  dumpStack()
  exit('1')

updateDomain()

print ''
print '-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-'
print 'Completed WLST OFFLINE successfully...!!!'
print '-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-'
print ''

########################################################################################################################
########################################################################################################################
########################################################################################################################

print ''
print '-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-'
print 'Beginning ONLINE configuration tasks'
print '-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-'
print ''

nmConnect(adminServer_Username, adminServer_Password, machine_ListenAddress, 5556, domain_Name, var_domain_dir, 'plain')

print ''
print '-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-'
print 'Connected to NODE MANAGER Successfully...!!!'
print '-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-'
print ''

nmStart('AdminServer')

## ONLINE CONFIG  #####################################################

connect(adminServer_Username, adminServer_Password, adminServer_URL)

print '@@@ Connected to AdminServer @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@'

edit()
startEdit()

configureDomain_online()

configureManagedServersOnline()

createCoherenceCluster_online(cohCluster_Name, cohCluster_ListenAddress, cohCluster_ListenPort,
                              jarray.array([ObjectName('com.bea:Name=' + cluster_Name + ',Type=Cluster')], ObjectName),
                              cohCluster_TTL)

createCoherenceServers_online(cohCluster_Name,
                              cohCluster_ListenAddress,
                              cohCluster_ListenPort,
                              cohServer_StartupArgs,
                              cohServer_Count,
                              cohServer_Classpath,
                              machine_Name)

createSpringWLDFModule_online()

## DEPLOY LIBRARIES #####################################################

save()
activate(block="true")

deploySharedLibraries()

shutdown()
