user="weblogic"
password="welcome1"
listenAddress="localhost"
listenPort=5556
domainName="mydomain"
domainDirectory="/u01/content/weblogic-innovation-seminars/WInS_Demos/grid-archive-examples/Oracle/Domains/mydomain"

print ''
print '============================================='
print 'Connecting to Node Manager...'
print '============================================='
print ''

nmConnect(user,password,listenAddress,listenPort,domainName,domainDirectory,'Plain')

print ''
print '============================================='
print 'Connected to NODE MANAGER Successfully'
print '============================================='
print ''


adminServerStatus= nmServerStatus('AdminServer');
if( adminServerStatus != 'RUNNING'):	
	nmStart('AdminServer')



connect(user,password, 't3://localhost:7001')

#Undeploying the Application
undeploy('ExampleGAR')
undeploy('ExampleEAR')
undeploy('ExampleListenerEAR')

nmKill('cache_server1')
nmKill('cache_server2')
nmKill('cache_server3')
nmKill('webapp_server1')
nmKill('webapp_server2')
nmKill('proxy_server1')
nmKill('proxy_server2')


edit()
startEdit()

cd('/Servers/cache_server1')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/cache_server1'))

cd('/')
cmo.destroyServer(getMBean('/Servers/cache_server1'))

cd('/Servers/cache_server2')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/cache_server2'))

cd('/')
cmo.destroyServer(getMBean('/Servers/cache_server2'))

cd('/Servers/cache_server3')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/cache_server3'))

cd('/')
cmo.destroyServer(getMBean('/Servers/cache_server3'))

cd('/Servers/proxy_server1')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/proxy_server1'))

cd('/')
cmo.destroyServer(getMBean('/Servers/proxy_server1'))

cd('/Servers/proxy_server2')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/proxy_server2'))

cd('/')
cmo.destroyServer(getMBean('/Servers/proxy_server2'))

cd('/Servers/webapp_server1')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/webapp_server1'))

cd('/')
cmo.destroyServer(getMBean('/Servers/webapp_server1'))

cd('/Servers/webapp_server2')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/webapp_server2'))

cd('/')
cmo.destroyServer(getMBean('/Servers/webapp_server2'))

activate()

startEdit()

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Clusters/CacheServerWebLogicCluster'))
cmo.destroyCluster(getMBean('/Clusters/CacheServerWebLogicCluster'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Clusters/ProxyWebLogicCluster'))
cmo.destroyCluster(getMBean('/Clusters/ProxyWebLogicCluster'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Clusters/WebAppWebLogicCluster'))
cmo.destroyCluster(getMBean('/Clusters/WebAppWebLogicCluster'))

activate()

startEdit()
cmo.destroyCoherenceClusterSystemResource(getMBean('/CoherenceClusterSystemResources/CoherenceCluster'))

activate()

startEdit()

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Machines/machine'))
cmo.destroyMachine(getMBean('/Machines/machine'))

activate()

shutdown()

