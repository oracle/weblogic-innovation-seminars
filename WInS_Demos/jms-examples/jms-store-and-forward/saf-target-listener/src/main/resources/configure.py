connect('weblogic','welcome1','t3://wins-vbox:7001')
edit()
startEdit()

cd('/')
cmo.createJDBCStore('saf-store-1')

cd('/JDBCStores/saf-store-1')
cmo.setDataSource(getMBean('/SystemResources/cluster-ds-nonXA'))
cmo.setPrefixName('SAFSTORE1')
set('Targets',jarray.array([ObjectName('com.bea:Name=ms-1 (migratable),Type=MigratableTarget')], ObjectName))

cd('/')
cmo.createJDBCStore('saf-store-2')

cd('/JDBCStores/saf-store-2')
cmo.setDataSource(getMBean('/SystemResources/cluster-ds-nonXA'))
cmo.setPrefixName('SAFSTORE2')
set('Targets',jarray.array([ObjectName('com.bea:Name=ms-2 (migratable),Type=MigratableTarget')], ObjectName))

cd('/')
cmo.createSAFAgent('saf-agent-1')

cd('/SAFAgents/saf-agent-1')
cmo.setStore(getMBean('/JDBCStores/saf-store-1'))
set('Targets',jarray.array([ObjectName('com.bea:Name=ms-1 (migratable),Type=MigratableTarget')], ObjectName))
cmo.setServiceType('Both')

cd('/')
cmo.createSAFAgent('saf-agent-2')

cd('/SAFAgents/saf-agent-2')
cmo.setStore(getMBean('/JDBCStores/saf-store-2'))
set('Targets',jarray.array([ObjectName('com.bea:Name=ms-2 (migratable),Type=MigratableTarget')], ObjectName))
cmo.setServiceType('Sending-only')

cd('/')
cmo.createJMSSystemResource('jms-module-saf')

cd('/SystemResources/jms-module-saf')
set('Targets',jarray.array([ObjectName('com.bea:Name=cluster-1,Type=Cluster')], ObjectName))

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf')
cmo.createUniformDistributedQueue('com/oracle/example/jms/saf/local-queue')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/UniformDistributedQueues/com/oracle/example/jms/saf/local-queue')
cmo.setJNDIName('com/oracle/example/jms/saf/local-queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf')
cmo.createConnectionFactory('com/oracle/example/jms/saf/cf')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/ConnectionFactories/com/oracle/example/jms/saf/cf')
cmo.setJNDIName('com/oracle/example/jms/saf/cf')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/ConnectionFactories/com/oracle/example/jms/saf/cf/SecurityParams/com/oracle/example/jms/saf/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/ConnectionFactories/com/oracle/example/jms/saf/cf/ClientParams/com/oracle/example/jms/saf/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/ConnectionFactories/com/oracle/example/jms/saf/cf/TransactionParams/com/oracle/example/jms/saf/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/ConnectionFactories/com/oracle/example/jms/saf/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/ConnectionFactories/com/oracle/example/jms/saf/cf/LoadBalancingParams/com/oracle/example/jms/saf/cf')
cmo.setLoadBalancingEnabled(true)
cmo.setServerAffinityEnabled(false)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf')
cmo.createSAFRemoteContext('remote-saf-context-1')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFRemoteContexts/remote-saf-context-1/SAFLoginContext/remote-saf-context-1')
cmo.setLoginURL('t3://wins-vbox:8101,wins-vbox:8102')
cmo.setUsername('weblogic')
setEncrypted('Password', 'Password_1295655228814', './examples/saf-server/wlst/Script1295655017245Config', './examples/saf-server/wlst/Script1295655017245Secret')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf')
cmo.createSAFErrorHandling('saf-error-handling')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFErrorHandlings/saf-error-handling')
cmo.setSAFErrorDestination(None)
cmo.setPolicy('Log')
cmo.setLogFormat(None)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf')
cmo.createSAFImportedDestinations('saf-imported-destinations')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFImportedDestinations/saf-imported-destinations')
cmo.setJNDIPrefix('saf/')
cmo.setSAFRemoteContext(getMBean('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFRemoteContexts/remote-saf-context-1'))
cmo.setSAFErrorHandling(getMBean('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFErrorHandlings/saf-error-handling'))
cmo.setTimeToLiveDefault(0)
cmo.setUseSAFTimeToLiveDefault(false)
cmo.setDefaultTargetingEnabled(true)

cd('/SAFAgents/saf-agent-1')
cmo.setAcknowledgeInterval(-1)
cmo.setForwardingPausedAtStartup(false)
cmo.setLoggingEnabled(true)
cmo.setReceivingPausedAtStartup(false)
cmo.setDefaultRetryDelayMultiplier(1.0)
cmo.setMessageBufferSize(-1)
cmo.setWindowSize(10)
cmo.setServiceType('Sending-only')
cmo.setConversationIdleTimeMaximum(0)
cmo.setDefaultTimeToLive(0)
cmo.setIncomingPausedAtStartup(false)
cmo.setDefaultRetryDelayMaximum(180000)
cmo.setDefaultRetryDelayBase(20000)
cmo.setWindowInterval(0)

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFImportedDestinations/saf-imported-destinations')
cmo.createSAFQueue('imported-saf-queue')

cd('/JMSSystemResources/jms-module-saf/JMSResource/jms-module-saf/SAFImportedDestinations/saf-imported-destinations/SAFQueues/imported-saf-queue')
cmo.setRemoteJNDIName('com/oracle/example/jms/base/queue')

save()
activate(block="true")
disconnect()
exit()
