import os
#loadProperties('../../wlst/cluster/weblogic-examples-domain.properties')
propsPath=os.environ["LABS_HOME"]+'/setup-packages/wlst/cluster/appgrid-domain.properties'
print propsPath
loadProperties(propsPath)

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

cd('/')
cmo.createJMSSystemResource('jms-module-uoo')

cd('/SystemResources/jms-module-uoo')
set('Targets',jarray.array([ObjectName('com.bea:Name='+cluster_Name+',Type=Cluster')], ObjectName))

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo')
cmo.createConnectionFactory('com/oracle/example/jms/uoo/cf')

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/ConnectionFactories/com/oracle/example/jms/uoo/cf')
cmo.setJNDIName('com/oracle/example/jms/uoo/cf')

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/ConnectionFactories/com/oracle/example/jms/uoo/cf/SecurityParams/com/oracle/example/jms/uoo/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/ConnectionFactories/com/oracle/example/jms/uoo/cf/ClientParams/com/oracle/example/jms/uoo/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/ConnectionFactories/com/oracle/example/jms/uoo/cf/TransactionParams/com/oracle/example/jms/uoo/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/ConnectionFactories/com/oracle/example/jms/uoo/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo')
cmo.createUniformDistributedQueue('com/oracle/example/jms/uoo/queue')

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/UniformDistributedQueues/com/oracle/example/jms/uoo/queue')
cmo.setJNDIName('com/oracle/example/jms/uoo/queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-uoo/JMSResource/jms-module-uoo/ConnectionFactories/com/oracle/example/jms/uoo/cf/LoadBalancingParams/com/oracle/example/jms/uoo/cf')
cmo.setLoadBalancingEnabled(true)
cmo.setServerAffinityEnabled(false)

save()
activate(block="true")
disconnect()
exit()
