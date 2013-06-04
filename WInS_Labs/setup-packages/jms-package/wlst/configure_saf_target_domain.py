import os
propsPath=os.environ["LABS_HOME"]+'/setup-packages/wlst/cluster/saf-target-domain.properties'
loadProperties(propsPath)

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

cd('/')
cmo.createJMSSystemResource('jms-module-saf-target')

cd('/SystemResources/jms-module-saf-target')
set('Targets',jarray.array([ObjectName('com.bea:Name='+cluster_Name+',Type=Cluster')], ObjectName))

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target')
cmo.createUniformDistributedQueue('com/oracle/example/jms/saf/local-queue')

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/UniformDistributedQueues/com/oracle/example/jms/saf/local-queue')
cmo.setJNDIName('com/oracle/example/jms/saf/local-queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target')
cmo.createConnectionFactory('com/oracle/example/jms/saf/cf')

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/ConnectionFactories/com/oracle/example/jms/saf/cf')
cmo.setJNDIName('com/oracle/example/jms/saf/cf')

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/ConnectionFactories/com/oracle/example/jms/saf/cf/SecurityParams/com/oracle/example/jms/saf/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/ConnectionFactories/com/oracle/example/jms/saf/cf/ClientParams/com/oracle/example/jms/saf/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/ConnectionFactories/com/oracle/example/jms/saf/cf/TransactionParams/com/oracle/example/jms/saf/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/ConnectionFactories/com/oracle/example/jms/saf/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target/ConnectionFactories/com/oracle/example/jms/saf/cf/LoadBalancingParams/com/oracle/example/jms/saf/cf')
cmo.setLoadBalancingEnabled(true)
cmo.setServerAffinityEnabled(false)

cd('/JMSSystemResources/jms-module-saf-target/JMSResource/jms-module-saf-target')

save()
activate(block="true")
disconnect()
exit()
