import time

from java.io import FileInputStream
from java.util import Properties

#################

def loadMyProperties(fileName):
    properties = Properties()
    input = FileInputStream(fileName)
    properties.load(input)
    input.close()
    result= {}
    for entry in properties.entrySet(): result[entry.key] = entry.value
    return result

#################

def getProperty(propName, default):
  try:
    return myProps[propName]
  except KeyError:
    return default

#################

def createTheDomain():
  print 'Creating domain name=[' +var_domain_name +'] in path=[' + var_domain_dir + ']...'
  createDomain(weblogic_home_path + '/common/templates/domains/wls.jar', var_domain_dir, var_adminServer_username, var_adminServer_Password)

  print 'Reading domain in WLST Offline Mode...'
  readDomain(var_domain_dir)

  print 'Setting Domain properties...'
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
  cmo.setAdministrationPort(int(var_adminServer_admin_port))

  print 'Setting Admin Listen Address to IP=[' + var_adminServer_listen_address + ']'
  cmo.setListenAddress(var_adminServer_listen_address)

  print 'Setting Admin Listen Port to [' + var_adminServer_listen_port + ']'
  cmo.setListenPort(int(var_adminServer_listen_port))

  cmo.setWeblogicPluginEnabled(false)
  cmo.setJavaCompiler('javac')
  cmo.setStartupMode('RUNNING')
  cmo.setVirtualMachineName(var_domain_name+'_AdminServer')
  cmo.setClientCertProxyEnabled(false)

#################

def createMachines():
  print 'Creating ' + str(var_managed_server_count) + ' Machines...'

  for n in range(1, var_managed_server_count+1):

    var_machine_name = 'm' + str(n)
    var_vm_pub_ip = getProperty('vm' + str(n) +'.ip.public', '')
    var_vm_pri_ip = getProperty('vm' + str(n) +'.ip.private', '')

    print 'Creating machine name=[' + var_machine_name + ']...'
    cd('/')
    create(var_machine_name, 'Machine')
    cd('/Machines/' + var_machine_name + '/')

    create(var_machine_name, 'NodeManager')
    cd('/Machines/' + var_machine_name + '/NodeManager/' + var_machine_name)

    ## JW: I suggest Plain NodeManager and they can worry about certificates, unless its easy to create and assign them
    set('NMType', 'Plain')

    print 'Setting Listen Address for machine=[' + var_machine_name + '] to IP=[' + var_vm_pri_ip + ']'
    set('ListenAddress', var_vm_pub_ip)
    set('ListenPort', int(var_nodemanager_listen_port))

#################

def createCluster():
  cd('/')
  print 'Creating cluster name=[' + var_cluster_name + ']...'
  create(var_cluster_name, 'Cluster')
  cd('/Clusters/' + var_cluster_name)

  # need to make this option selectable
  cmo.setMigrationBasis('consensus')

  # need to loop this and create the array
  # set('CandidateMachinesForMigratableServers',jarray.array([ObjectName('com.bea:Name=m1,Type=Machine'), ObjectName('com.bea:Name=m2,Type=Machine')], ObjectName))

#################

def createManagedServers():
  print 'Creating ' + str(var_managed_server_count) + ' Servers...'
  for n in range(1, var_managed_server_count+1):

    var_machine_name = 'm' + str(n)
    var_ms_name = var_ms_name_base + '-' + str(n)
    var_ms_listen_port = getProperty(var_ms_name +'.port.public', int(str(var_ms_port_base) + str(n)))
    var_ms_listen_address = getProperty('vm' + str(n) + '.ip.public', '')
    var_vm_pub_ip = getProperty('vm' + str(n) +'.ip.public', '')
    var_vm_pri_ip = getProperty('vm' + str(n) +'.ip.private', '')

    cd('/')
    print 'Creating Server Name=[' + var_ms_name + ']...'
    create(var_ms_name, 'Server')

    cd('/Servers/' + var_ms_name)

    print 'Setting Listen Port=[' + str(var_ms_listen_port) + '] for Managed Server name=[' + var_ms_name + ']...'
    set('ListenPort', int(var_ms_listen_port))

    print 'Setting Listen Address=[' + str(var_ms_listen_address) + '] for Managed Server name=[' + var_ms_name + ']...'
    set('ListenAddress', var_ms_listen_address)

    print 'Setting Machine=[' + var_machine_name + '] for Managed Server name=[' + var_ms_name + '] ...'
    set('Machine', var_machine_name)

    print 'Assigning Server [' + var_ms_name + '] to cluster [' + var_cluster_name + ']...'
    assign('Server', var_ms_name, 'Cluster', var_cluster_name)

#############

def createJMSServers():
  print 'Creating ' + str(var_managed_server_count) + ' JMS Servers...'
  for n in range(1, var_managed_server_count+1):

    var_ms_name = var_ms_name_base + '-' + str(n)
    var_jms_server_name = var_jms_sever_name_base + '-' + str(n)
    var_jms_store_type = getProperty('jms.store.type', 'file')

    if var_jms_store_type == 'file':
      var_persistent_store_name = 'jms-file-store-' + str(n)
      var_dir_name = '/shared/' + var_domain_name + '/' + var_cluster_name +'/' + var_persistent_store_name

      cd('/')
      print 'Creating FileStore name=[' + var_persistent_store_name + ']'
      var_persistent_store = create(var_persistent_store_name, 'FileStore')

      print 'Setting directory to [' + var_dir_name + ']...'
      cd('/FileStores/' + var_persistent_store_name)
      cmo.setDirectory(var_dir_name)

      print 'Created FileStore'

    elif var_jms_store_type == 'jdbc':
      cd('/')
      var_persistent_store_name = 'jms-jdbc-store-' + str(n)
      var_persistent_store = create(var_persistent_store_name, 'JDBCStore')
      print 'TODO: Configure JDBC store'
    else:
      print 'Unsupported JMS Store Type:' + var_jms_store_type

    #myObjName='com.bea:Name=ms-' + str(n) + ' (migratable),Type=MigratableTarget'
    #print 'Setting targets...'
    #set('Targets',jarray.array([ObjectName(myObjName)], ObjectName))

    print 'Creating JMS Server Name=[' + var_jms_server_name +']...'
    create(var_jms_server_name, 'JMSServer')

    print 'Assigning JMS Server [' + var_jms_server_name + '] to server [' + var_ms_name + ']...'
    assign('JMSServer', var_jms_server_name, 'Target', var_ms_name)

    if var_persistent_store != None:
      cd('/JMSServers/' + var_jms_server_name)
      cmo.setPersistentStore(var_persistent_store)

      # If we want to do migratable targets...
    #set('Targets',jarray.array([ObjectName('com.bea:Name=ms-' + str(n) + ' (migratable),Type=MigratableTarget')], ObjectName))



#for n in range(1, int(var_managed_server_count)+1):
#
#  var_ms_name = var_ms_name_base + '-' + str(n)
#  cd('/MigratableTargets/' + var_ms_name +' (migratable)')
#  cmo.setMigrationPolicy('failure-recovery')
#  cmo.setUserPreferredServer(getMBean('/Servers/' + var_ms_name))
#
#  # need to loop and create array
#  set('ConstrainedCandidateServers',jarray.array([ObjectName('com.bea:Name=ms-1,Type=Server'), ObjectName('com.bea:Name=ms-2,Type=Server')], ObjectName))

#####################################################################################################################
#####################################################################################################################

myProps = loadMyProperties('domain.properties')

loadProperties('domain.properties')

# JW: I fully accept that global variables are lazy but this is a work in progress :)

var_domains_dir = user_projects_path + '/domains/'
var_domain_name = getProperty('domain.name', 'DefaultDomain')
var_domain_dir = var_domains_dir + var_domain_name

var_adminServer_username = getProperty('adminServer.username', 'weblogic')
var_adminServer_Password = getProperty('adminServer.password', 'welcome1')
var_adminServer_listen_address = getProperty('vm0.ip.public', '')
var_adminServer_listen_port = getProperty('adminServer.ListenPort', '7001')
var_adminServer_admin_port = getProperty('adminServer.AdministrationPort', '7200')
var_cluster_name = getProperty('cluster.name', 'cluster-1')
var_managed_server_count = int(getProperty('managed.server.count', '10'))
var_ms_name_base = getProperty('managed.server.name.base', 'ms')
var_ms_port_base = getProperty('managed.server.port.base', '7100')
var_jms_sever_name_base = getProperty('jms.sever.name.base', 'jms-server')
var_nodemanager_listen_port = getProperty('nodemanager.listen.port', '5556')


createTheDomain()
createMachines()
createCluster()
createManagedServers()

var_jms_enabled = getProperty('jms.enabled', 'false')
if var_jms_enabled == 'true':
  createJMSServers()

updateDomain()
exit()