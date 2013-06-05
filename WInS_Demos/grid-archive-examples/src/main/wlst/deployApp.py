user="weblogic"
password="welcome1"
listenAddress="localhost"
listenPort=5556
domainName="base_domain"
domainDirectory="/u01/wls1212/user_projects/domains/base_domain"

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


start('cache_server1','Server')
start('cache_server2','Server')
start('webapp_server1','Server')
start('webapp_server2','Server')


#Deploying ExampleGAR.gar application to CacheServerWebLogicCluster
deploy('ExampleGAR.gar','/u01/content/weblogic-innovations-seminars/WInS_Demos/grid-archive-examples/src/main/apps/ExampleGAR.gar',targets='CacheServerWebLogicCluster')
#startApplication('ExampleGAR.gar')

#Deploying ExampleEAR.ear application to WebAppWebLogicCluster
deploy('ExampleEAR.ear','/u01/content/weblogic-innovations-seminars/WInS_Demos/grid-archive-examples/src/main/apps/ExampleEAR.ear',targets='WebAppWebLogicCluster')
#startApplication('ExampleEAR.ear')
