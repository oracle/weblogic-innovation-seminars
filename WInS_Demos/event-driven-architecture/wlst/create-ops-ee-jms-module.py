loadProperties('ops-domain.properties')

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

cd('/')
cmo.createJMSSystemResource('jms-module-ops')

cd('/JMSSystemResources/jms-module-ops')
set('Targets', jarray.array([ObjectName('com.bea:Name=cluster-1,Type=Cluster')], ObjectName))

cmo.createSubDeployment('cluster-subdeployment')

cd('/JMSSystemResources/jms-module-ops/SubDeployments/cluster-subdeployment')
set('Targets', jarray.array(
    [ObjectName('com.bea:Name=jms-server-1,Type=JMSServer'), ObjectName('com.bea:Name=jms-server-2,Type=JMSServer')],
    ObjectName))

cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
cmo.createConnectionFactory('com.oracle.demo.ops.jms.cf')

cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf')
cmo.setJNDIName('com.oracle.demo.ops.jms.cf')

cd(
    '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf/SecurityParams/com.oracle.demo.ops.jms.cf')
cmo.setAttachJMSXUserId(false)

cd(
    '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf/ClientParams/com.oracle.demo.ops.jms.cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd(
    '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf/TransactionParams/com.oracle.demo.ops.jms.cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/ConnectionFactories/com.oracle.demo.ops.jms.cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
cmo.createUniformDistributedQueue('com.oracle.demo.ops.jms.eventQueue')

cd(
    '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/UniformDistributedQueues/com.oracle.demo.ops.jms.eventQueue')
cmo.setJNDIName('com.oracle.demo.ops.jms.eventQueue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
cmo.createUniformDistributedQueue('com.oracle.demo.ops.jms.shipmentQueue')

cd(
    '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/UniformDistributedQueues/com.oracle.demo.ops.jms.shipmentQueue')
cmo.setJNDIName('com.oracle.demo.ops.jms.shipmentQueue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops')
cmo.createUniformDistributedTopic('com.oracle.demo.ops.jms.eventTopic')

cd(
    '/JMSSystemResources/jms-module-ops/JMSResource/jms-module-ops/UniformDistributedTopics/com.oracle.demo.ops.jms.eventTopic')
cmo.setJNDIName('com.oracle.demo.ops.jms.eventTopic')
cmo.setForwardingPolicy('Partitioned')
cmo.setDefaultTargetingEnabled(false)
cmo.setSubDeploymentName('cluster-subdeployment')

save()
activate(block="true")
disconnect()
exit()
