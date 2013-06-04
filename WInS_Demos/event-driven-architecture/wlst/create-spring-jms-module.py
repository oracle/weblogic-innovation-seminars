loadProperties('ops-domain.properties')
connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

jms_module_name = 'jms-module-ops-spring'

cd('/')
cmo.createJMSSystemResource(jms_module_name)

cd('/JMSSystemResources/' + jms_module_name)
set('Targets', jarray.array([ObjectName('com.bea:Name=cluster-1,Type=Cluster')], ObjectName))

for n in range(1, int(managed_server_count) + 1):
    managed_server_name = managed_server_name_base + '-' + str(n)
    managed_server_listen_port = int(str(managed_server_port_base) + str(n))
    jms_server_name = jms_sever_name_base + '-' + str(n)
    foreign_server_name = 'ops-spring-foreign-server-' + str(n)

    cd('/JMSSystemResources/' + jms_module_name)
    cmo.createSubDeployment(jms_server_name + '-subdeployment')

    cd('/JMSSystemResources/' + jms_module_name + '/SubDeployments/' + jms_server_name + '-subdeployment')
    set('Targets', jarray.array([ObjectName('com.bea:Name=' + jms_server_name + ',Type=JMSServer')], ObjectName))

    cd('/JMSSystemResources/' + jms_module_name + '/JMSResource/' + jms_module_name)
    cmo.createForeignServer(foreign_server_name)

    cd(
        '/JMSSystemResources/' + jms_module_name + '/JMSResource/' + jms_module_name + '/ForeignServers/' + foreign_server_name)
    cmo.setDefaultTargetingEnabled(false)
    cmo.setSubDeploymentName(jms_server_name + '-subdeployment')

    cd(
        '/JMSSystemResources/' + jms_module_name + '/JMSResource/' + jms_module_name + '/ForeignServers/' + foreign_server_name)
    cmo.createForeignDestination('com.oracle.demo.ops.jms.eventTopic-' + str(n))

    cd(
        '/JMSSystemResources/' + jms_module_name + '/JMSResource/' + jms_module_name + '/ForeignServers/' + foreign_server_name + '/ForeignDestinations/com.oracle.demo.ops.jms.eventTopic-' + str(
            n))
    cmo.setRemoteJNDIName(jms_server_name + '@com.oracle.demo.ops.jms.eventTopic')
    cmo.setLocalJNDIName('foreign.com.oracle.demo.ops.jms.eventTopic')

save()
activate(block="true")
disconnect()
exit()
