import os
#loadProperties('../../wlst/cluster/weblogic-examples-domain.properties')
propsPath=os.environ["LABS_HOME"]+'/setup-packages/wlst/cluster/appgrid-domain.properties'
loadProperties(propsPath)


connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

cd('/')
cmo.createJMSSystemResource('jms-module-uow')

cd('/SystemResources/jms-module-uow')
set('Targets',jarray.array([ObjectName('com.bea:Name='+cluster_Name+',Type=Cluster')], ObjectName))

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow')
cmo.createConnectionFactory('com/oracle/example/jms/uow/cf')

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/ConnectionFactories/com/oracle/example/jms/uow/cf')
cmo.setJNDIName('com/oracle/example/jms/uow/cf')

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/ConnectionFactories/com/oracle/example/jms/uow/cf/SecurityParams/com/oracle/example/jms/uow/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/ConnectionFactories/com/oracle/example/jms/uow/cf/ClientParams/com/oracle/example/jms/uow/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/ConnectionFactories/com/oracle/example/jms/uow/cf/TransactionParams/com/oracle/example/jms/uow/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/ConnectionFactories/com/oracle/example/jms/uow/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow')
cmo.createUniformDistributedQueue('com/oracle/example/jms/uow/queue')

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/UniformDistributedQueues/com/oracle/example/jms/uow/queue')
cmo.setJNDIName('com/oracle/example/jms/uow/queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow')
cmo.createUniformDistributedQueue('com/oracle/example/jms/uow/error-queue')

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/UniformDistributedQueues/com/oracle/example/jms/uow/error-queue')
cmo.setJNDIName('com/oracle/example/jms/uow/error-queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/ConnectionFactories/com/oracle/example/jms/uow/cf/LoadBalancingParams/com/oracle/example/jms/uow/cf')
cmo.setLoadBalancingEnabled(true)
cmo.setServerAffinityEnabled(false)

cd('/JMSSystemResources/jms-module-uow/JMSResource/jms-module-uow/UniformDistributedQueues/com/oracle/example/jms/uow/queue')
cmo.unSet('Template')
cmo.setLoadBalancingPolicy('Round-Robin')
cmo.setResetDeliveryCountOnForward(true)
cmo.setIncompleteWorkExpirationTime(30000)
cmo.setForwardDelay(-1)
cmo.setAttachSender('supports')
cmo.setSAFExportPolicy('All')
cmo.setProductionPausedAtStartup(false)
cmo.setDefaultUnitOfOrder(false)
cmo.setUnitOfOrderRouting('Hash')
cmo.setUnitOfWorkHandlingPolicy('SingleMessageDelivery')
cmo.setInsertionPausedAtStartup(false)
cmo.setMessagingPerformancePreference(25)
cmo.setConsumptionPausedAtStartup(false)



save()
activate(block="true")
disconnect()
exit()
