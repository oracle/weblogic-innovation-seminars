import os
#loadProperties('../../wlst/cluster/weblogic-examples-domain.properties')
propsPath=os.environ["LABS_HOME"]+'/setup-packages/wlst/cluster/appgrid-domain.properties'

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)
edit()
startEdit()

cmo.createJMSSystemResource('jms-module-saf-client')

cd('/SystemResources/jms-module-saf-client')
set('Targets',jarray.array([ObjectName('com.bea:Name='+cluster_Name+',Type=Cluster')], ObjectName))

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client')
cmo.createConnectionFactory('com/oracle/example/jms/saf/client/cf')

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/ConnectionFactories/com/oracle/example/jms/saf/client/cf')
cmo.setJNDIName('com/oracle/example/jms/saf/client/cf')

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/ConnectionFactories/com/oracle/example/jms/saf/client/cf/SecurityParams/com/oracle/example/jms/saf/client/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/ConnectionFactories/com/oracle/example/jms/saf/client/cf/ClientParams/com/oracle/example/jms/saf/client/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/ConnectionFactories/com/oracle/example/jms/saf/client/cf/TransactionParams/com/oracle/example/jms/saf/client/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/ConnectionFactories/com/oracle/example/jms/saf/client/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client')
cmo.createUniformDistributedQueue('com/oracle/example/jms/saf/client/queue')

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/UniformDistributedQueues/com/oracle/example/jms/saf/client/queue')
cmo.setJNDIName('com/oracle/example/jms/saf/client/queue')
cmo.unSet('Template')
cmo.setLoadBalancingPolicy('Round-Robin')
cmo.setResetDeliveryCountOnForward(true)
cmo.setIncompleteWorkExpirationTime(-1)
cmo.setForwardDelay(-1)
cmo.setAttachSender('supports')
cmo.setSAFExportPolicy('All')
cmo.setProductionPausedAtStartup(false)
cmo.setDefaultUnitOfOrder(false)
cmo.setDefaultTargetingEnabled(false)
cmo.setUnitOfOrderRouting('Hash')
cmo.setUnitOfWorkHandlingPolicy('PassThrough')
cmo.setInsertionPausedAtStartup(false)
cmo.setMessagingPerformancePreference(25)
cmo.setConsumptionPausedAtStartup(false)

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client')
cmo.createUniformDistributedQueue('com/oracle/example/jms/saf/client/queue-invalid')

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/UniformDistributedQueues/com/oracle/example/jms/saf/client/queue-invalid')
cmo.setJNDIName('com/oracle/example/jms/saf/client/queue-invalid')
cmo.setDefaultTargetingEnabled(true)
cmo.unSet('Template')
cmo.setLoadBalancingPolicy('Round-Robin')
cmo.setResetDeliveryCountOnForward(true)
cmo.setIncompleteWorkExpirationTime(-1)
cmo.setForwardDelay(-1)
cmo.setAttachSender('supports')
cmo.setSAFExportPolicy('None')
cmo.setProductionPausedAtStartup(false)
cmo.setDefaultUnitOfOrder(false)
cmo.setUnitOfOrderRouting('Hash')
cmo.setUnitOfWorkHandlingPolicy('PassThrough')
cmo.setInsertionPausedAtStartup(false)
cmo.setMessagingPerformancePreference(25)
cmo.setConsumptionPausedAtStartup(false)

cd('/JMSSystemResources/jms-module-saf-client/JMSResource/jms-module-saf-client/ConnectionFactories/com/oracle/example/jms/saf/client/cf/LoadBalancingParams/com/oracle/example/jms/saf/client/cf')
cmo.setLoadBalancingEnabled(true)
cmo.setServerAffinityEnabled(false)

save()
activate(block="true")
disconnect()
exit()
