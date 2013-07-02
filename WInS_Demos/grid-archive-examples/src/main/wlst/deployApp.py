# Author:  Tim Middleton
# Date:    2012-05-07
# Purpose: This WLST script deploys the artifacts to the domain, previously created
#          by the create_example.py script. 
#          This script takes the argument of the examples base directory.
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

start('cache_server1','Server')
start('cache_server2','Server')
start('webapp_server1','Server')
start('webapp_server2','Server')
start('proxy_server1','Server')
start('proxy_server2','Server')

ExampleGAR         ='/u01/content/weblogic-innovation-seminars/WInS_Demos/grid-archive-examples/src/main/apps/ExampleGAR.gar'
ExampleEAR         ='/u01/content/weblogic-innovation-seminars/WInS_Demos/grid-archive-examples/src/main/apps/ExampleEAR.ear'
ExampleListenerEAR ='/u01/content/weblogic-innovation-seminars/WInS_Demos/grid-archive-examples/src/main/apps/ExampleListenerEAR.ear'

cacheClusterName='CacheServerWebLogicCluster'
applicationClusterName='WebAppWebLogicCluster'
proxyClusterName='ProxyWebLogicCluster'

print 'Deploying GAR file '

deploy('ExampleGAR',ExampleGAR, targets=cacheClusterName)
deploy('ExampleGAR',ExampleGAR, targets=proxyClusterName)
deploy('ExampleEAR',ExampleEAR, targets=applicationClusterName)

deploy('ExampleListenerEAR',ExampleListenerEAR, targets="webapp_server1")


exit()


