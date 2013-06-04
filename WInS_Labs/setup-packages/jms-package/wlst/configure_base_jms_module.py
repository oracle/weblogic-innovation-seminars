loadProperties('../../wlst/cluster/weblogic-examples-domain.properties')

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)


jms_module_name=jms-module-base

edit()

startEdit()

cd('/')
cmo.createJMSSystemResource(jms_module_name)

cd('/JMSSystemResources/'+jms_module_name+'')
set('Targets',jarray.array([ObjectName('com.bea:Name='+cluster_Name+',Type=Cluster')], ObjectName))

cmo.createSubDeployment('cluster-subdeployment')

cd('/JMSSystemResources/'+jms_module_name+'/SubDeployments/cluster-subdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=jms-server-1,Type=JMSServer'), ObjectName('com.bea:Name=jms-server-2,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'')
cmo.createConnectionFactory('com/oracle/example/jms/base/cf')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/ConnectionFactories/com/oracle/example/jms/base/cf')
cmo.setJNDIName('com/oracle/example/jms/base/cf')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/ConnectionFactories/com/oracle/example/jms/base/cf/SecurityParams/com/oracle/example/jms/base/cf')
cmo.setAttachJMSXUserId(false)

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/ConnectionFactories/com/oracle/example/jms/base/cf/ClientParams/com/oracle/example/jms/base/cf')
cmo.setClientIdPolicy('Restricted')
cmo.setSubscriptionSharingPolicy('Exclusive')
cmo.setMessagesMaximum(10)

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/ConnectionFactories/com/oracle/example/jms/base/cf/TransactionParams/com/oracle/example/jms/base/cf')
cmo.setXAConnectionFactoryEnabled(true)

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/ConnectionFactories/com/oracle/example/jms/base/cf')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'')
cmo.createUniformDistributedQueue('com/oracle/example/jms/base/queue')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedQueues/com/oracle/example/jms/base/queue')
cmo.setJNDIName('com/oracle/example/jms/base/queue')
cmo.setDefaultTargetingEnabled(true)

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'')
cmo.createUniformDistributedTopic('com/oracle/example/jms/base/topic')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/topic')
cmo.setJNDIName('com/oracle/example/jms/base/topic')
cmo.setForwardingPolicy('Replicated')

cd('/JMSSystemResources/'+jms_module_name+'/SubDeployments/cluster-subdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=jms-server-1,Type=JMSServer'), ObjectName('com.bea:Name=jms-server-2,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/topic')
cmo.setSubDeploymentName('cluster-subdeployment')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'')
cmo.createUniformDistributedTopic('com/oracle/example/jms/base/clearscreen')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/clearscreen')
cmo.setJNDIName('com/oracle/example/jms/base/clearscreen')
cmo.setForwardingPolicy('Replicated')

cd('/JMSSystemResources/'+jms_module_name+'/SubDeployments/cluster-subdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=jms-server-1,Type=JMSServer'), ObjectName('com.bea:Name=jms-server-2,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/clearscreen')
cmo.setSubDeploymentName('cluster-subdeployment')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'')
cmo.createUniformDistributedTopic('com/oracle/example/jms/base/replicated-topic')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/replicated-topic')
cmo.setJNDIName('com/oracle/example/jms/base/replicated-topic')
cmo.setForwardingPolicy('Replicated')

cd('/JMSSystemResources/'+jms_module_name+'/SubDeployments/cluster-subdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=jms-server-1,Type=JMSServer'), ObjectName('com.bea:Name=jms-server-2,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/replicated-topic')
cmo.setSubDeploymentName('cluster-subdeployment')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'')
cmo.createUniformDistributedTopic('com/oracle/example/jms/base/partitioned-topic')

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/partitioned-topic')
cmo.setJNDIName('com/oracle/example/jms/base/partitioned-topic')
cmo.setForwardingPolicy('Partitioned')

cd('/JMSSystemResources/'+jms_module_name+'/SubDeployments/cluster-subdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=jms-server-1,Type=JMSServer'), ObjectName('com.bea:Name=jms-server-2,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/'+jms_module_name+'/JMSResource/'+jms_module_name+'/UniformDistributedTopics/com/oracle/example/jms/base/partitioned-topic')
cmo.setSubDeploymentName('cluster-subdeployment')

save()
activate(block="true")
disconnect()
exit()

