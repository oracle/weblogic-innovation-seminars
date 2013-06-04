
loadProperties('counter.properties')

#parameter Declaration

var_domain_dir = USER_PROJECTS + domainName

print ''
print '============================================='
print 'Connecting to Node Manager...'
print '============================================='
print ''

nmConnect(user,password,listenAddress,listenPort,domainName,var_domain_dir,'plain')

print ''
print '============================================='
print 'Connected to NODE MANAGER Successfully'
print '============================================='
print ''

############################################################################################################################################
#BEGIN MAIN
#print 'Creating domain in path=' + var_domain_dir
#createDomain( WL_HOME +'common/templates/wls/wls.jar',USER_PROJECTS+'weblogic-cluster-domain','weblogic','welcome1')
#print 'domain created'
#readDomain(var_domain_dir)
#print 'read domain'

#startNodaManager()

adminServerStatus= nmServerStatus('AdminServer');
if( adminServerStatus != 'RUNNING'):									
	nmStart('AdminServer')

connect(user,password, 't3://' + listenAddress + ':' + str(adminPort))
edit()
startEdit()

cd('/')
cmo.createServer('LoadBalancer')

cd('/Servers/LoadBalancer')
cmo.setListenAddress('wins-vbox.localdomain')
cmo.setListenPort(7002)

activate()

startEdit()

cd('/')
cmo.createMachine('MyMachine')

cd('/Machines/MyMachine/NodeManager/MyMachine')
cmo.setNMType('Plain')
cmo.setListenPort(5556)

activate()

startEdit()

cd('/Servers/LoadBalancer')
cmo.setMachine(getMBean('/Machines/MyMachine'))
cmo.setCluster(None)

cd('/Servers/LoadBalancer/SSL/LoadBalancer')
cmo.setEnabled(false)

activate()

startEdit()

cd('/')
cmo.createServerTemplate('MyCluster-Template')

cd('/ServerTemplates/MyCluster-Template')
cmo.setListenPort(7002)

cd('/ServerTemplates/MyCluster-Template/SSL/MyCluster-Template')
cmo.setListenPort(8100)

cd('/ServerTemplates/MyCluster-Template')
cmo.setMachine(getMBean('/Machines/MyMachine'))

cd('/')
cmo.createCluster('MyCluster')

cd('/Clusters/MyCluster')
cmo.setClusterMessagingMode('unicast')

cd('/ServerTemplates/MyCluster-Template')
cmo.setCluster(getMBean('/Clusters/MyCluster'))

cd('/Clusters/MyCluster/DynamicServers/MyCluster')
cmo.setServerTemplate(getMBean('/ServerTemplates/MyCluster-Template'))
cmo.setMaximumDynamicServerCount(3)
cmo.setCalculatedListenPorts(true)
cmo.setCalculatedMachineNames(false)
cmo.setCalculatedListenPorts(true)
cmo.setServerNamePrefix('MyCluster-')

save()
activate()


shutdown()
