import time

loadProperties('environment.properties')

########################################################################################################################

domain_Name = 'weblogic-basic-domain'
machine_ListenAddress = 'wins-vbox.localdomain'
jmsServer_BaseName = 'jms-server'
cluster_Name = 'cluster-1'
machine_Name = 'wins-vbox.localdomain'

wins_demos_home = '/u01/content/weblogic-innovations-seminars/WInS_Demos'

########################################################################################################################

datasource_JndiName = 'jdbc.ds.weblogic_examples'
datasource_GlobalTransactions = 'None'
datasource_JdbcDriver = 'oracle.jdbc.OracleDriver'
datasource_User = 'weblogic_basic_domain'
datasource_Password = 'weblogic_basic_domain'

########################################################################################################################

COHERENCE_LIB = COHERENCE_HOME + '/lib/coherence.jar'
COHERENCE_WEB_LIB = COHERENCE_HOME + '/lib/coherence-web-spi.war'
ACTIVE_CACHE_LIB = WL_HOME + '/common/deployable-libraries/active-cache-1.0.jar'
WLS_SPRING_LIB = WL_HOME + '/server/lib/weblogic-spring.jar'
TOPLINK_GRID_LIB = MW_HOME + '/oracle_common/modules/oracle.toplink_12.1.2/toplink-grid.jar'

########################################################################################################################

adminServer_ListenAddress = 'wins-vbox.localdomain'
adminServer_ListenPort = 9001
adminServer_AdministrationPort = 9200
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
managedServer_BasePort = '710'
managedServer_BaseAdminPort = '720'
managedServer_StartupArgs = '-XX:FlightRecorderOptions=defaultrecording=true '\
                            ' -Xms256m -Xmx512m '\
                            ' -Dtangosol.coherence.ttl=0 '\
                            ' -Dtangosol.coherence.distributed.localstorage=false '\
                            ' -Dtangosol.coherence.session.localstorage=false'\
                            ' -Dtangosol.coherence.cacheconfig=/coherence-cache-config.xml'

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

var_domain_dir = USER_PROJECTS + '/domains/' + domain_Name
print 'Creating domain in path=' + var_domain_dir

try:
  createDomain(WL_HOME + '/common/templates/wls/wls.jar', var_domain_dir, adminServer_Username, adminServer_Password)
  print 'domain created'

  readDomain(var_domain_dir)
  print 'read domain'

  cd('/')


  cd('/Servers/AdminServer')
  cmo.setListenPortEnabled(true)
  cmo.setAdministrationPort(int(adminServer_AdministrationPort))
  cmo.setAdministrationPortEnabled(true)
  cmo.setListenPort(int(adminServer_ListenPort))
  #  cmo.setListenAddress(adminServer_ListenAddress)
  cmo.setVirtualMachineName(domain_Name + '_AdminServer')

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
