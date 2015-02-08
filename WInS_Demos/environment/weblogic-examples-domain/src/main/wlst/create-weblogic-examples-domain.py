import time


########################################################################################################################

domain_Name = 'weblogic-examples-domain'
machine_ListenAddress = 'wins-vbox.localdomain'
machine_Name = 'wins-vbox.localdomain'

wins_demos_home = '/u01/content/weblogic-innovation-seminars/WInS_Demos'

adminServer_ListenAddress = 'wins-vbox.localdomain'
adminServer_ListenPort = 7001
adminServer_AdministrationPort = 7200
adminServer_Username = 'weblogic'
adminServer_Password = 'welcome1'
adminServer_URL = 't3://' + adminServer_ListenAddress + ':' + str(adminServer_ListenPort)
adminServer_StartupArgs = '-Xms=256m -Xmx=256m' \
                          ' -Dweblogic.nodemanager.sslHostNameVerificationEnabled=false' \
                          ' -Dweblogic.security.SSL.ignoreHostnameVerify=true' \
                          ' -Dweblogic.security.SSL.ignoreHostnameVerification=true' \
                          ' -Dweblogic.security.TrustKeyStore=DemoTrust'

# JAVA_HOME='/u01/content/weblogic-innovation-seminars/WInS_Demos/JBossToWLS/JBoss4-5ToWLS12/jdk1.6.0_35'

########################################################################################################################


var_domain_dir = USER_PROJECTS + '/domains/' + domain_Name
print 'Creating domain in path=' + var_domain_dir
print 'JAVA_HOME is' + JAVA_HOME
 
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
  set('TunnelingEnabled', true)
  cmo.setListenPortEnabled(true)
  cmo.setAdministrationPort(int(adminServer_AdministrationPort))
  cmo.setListenPort(int(adminServer_ListenPort))
  #cmo.setListenAddress(adminServer_ListenAddress)
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

