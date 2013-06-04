import time

#loadProperties('test-domain.properties')

var_domain_dir = user_projects_path + '/domains/'+domain_name
print 'Creating domain in path=' + var_domain_dir

createDomain(weblogic_home_path + '/common/templates/domains/wls.jar', var_domain_dir, adminServer_Username, adminServer_Password)
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
cmo.setWeblogicPluginEnabled(false)
cmo.setJavaCompiler('javac')
cmo.setStartupMode('RUNNING')
cmo.setVirtualMachineName(domain_name+'_AdminServer')
cmo.setClientCertProxyEnabled(false)

print 'updating domain'
updateDomain()

cd('/')
create(machine_name, 'Machine')

cd('/Machines/'+machine_name+'/')
create(machine_name, 'NodeManager')

cd('/Machines/'+machine_name+'/NodeManager/'+machine_name)

set('NMType', 'Plain')
set('ListenAddress', '127.0.0.1')
set('ListenPort', 5556)

cd('/')
create(cluster_Name, 'Cluster')

for n in range(1, int(managed_server_count)+1):

  cd('/')
  managed_server_name = managed_server_name_base + '-' + str(n)
  managed_server_listen_port = int(str(managed_server_port_base) + str(n))
  jms_server_name = jms_sever_name_base + '-' + str(n)

  print 'Creating Server Name=[' + managed_server_name + ']'
  create(managed_server_name, 'Server')
  cd('/Servers/' + managed_server_name)

  print 'Setting Listen Port: ' + str(managed_server_listen_port)
  set('ListenPort', int(managed_server_listen_port))

  print 'Setting Machine=['+machine_name+']'
  set('Machine', machine_name)

  print 'Assigning Server [' + managed_server_name + '] to cluster [' + cluster_Name + ']'
  assign('Server', managed_server_name, 'Cluster', cluster_Name)

  print 'Creating JMS Server Name=[' + jms_server_name +']'
  create(jms_server_name, 'JMSServer')

  print 'Assigning JMS Server [' + jms_server_name + '] to server [' + managed_server_name + ']'
  assign('JMSServer', jms_server_name, 'Target', managed_server_name)

updateDomain()
exit()