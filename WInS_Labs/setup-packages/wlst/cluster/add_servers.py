import time

#loadProperties('test-domain.properties')


for n in range(numCurrentServers, int(numServersToAdd)):

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