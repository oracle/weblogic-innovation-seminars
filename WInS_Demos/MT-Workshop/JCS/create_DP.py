nmConnect('weblogic','welcome1',domainName='base_domain',port='5556',host='localhost')
nmStart('AdminServer')
nmDisconnect()

connect('weblogic','welcome1','t3://localhost:7001')

edit()

# Creating Machine in base_domain
startEdit()
cd('/')
cmo.createUnixMachine('machine')
cd('/Machines/machine/NodeManager/machine')
cmo.setNMType('SSL')
cmo.setListenAddress('localhost')
cmo.setListenPort(5556)
cmo.setDebugEnabled(false)
activate()

#Creating Dynamic Cluster in base_domain
startEdit()
cd('/')
cmo.createServerTemplate('app-cluster-Template')
cd('/ServerTemplates/app-cluster-Template')
cmo.setListenPort(7100)
cd('/ServerTemplates/app-cluster-Template/SSL/app-cluster-Template')
cmo.setListenPort(8100)
cd('/ServerTemplates/app-cluster-Template')
cmo.setMachine(getMBean('/Machines/machine'))
cd('/')
cmo.createCluster('app-cluster')
cd('/Clusters/app-cluster')
cmo.setClusterMessagingMode('unicast')
cd('/ServerTemplates/app-cluster-Template')
cmo.setCluster(getMBean('/Clusters/app-cluster'))
cd('/Clusters/app-cluster/DynamicServers/app-cluster')
cmo.setServerTemplate(getMBean('/ServerTemplates/app-cluster-Template'))
cmo.setDynamicClusterSize(2)
cmo.setMaxDynamicClusterSize(8)
cmo.setCalculatedListenPorts(true)
cmo.setCalculatedMachineNames(false)
cmo.setCalculatedListenPorts(true)
cmo.setServerNamePrefix('app-cluster-')
activate()

disconnect()




# Connecting to Admin Server to create the Partitions
connect('weblogic','welcome1','t3://localhost:7001')

start('app-cluster','Cluster')

edit()

# Creating Virtual Target and Domain partition dp1

startEdit()
cd('/')
cmo.createVirtualTarget('VT1')
cd('/VirtualTargets/VT1')
cmo.setHostNames(array(["localhost"],java.lang.String))
cmo.setUriPrefix('/dp1')
set('Targets',jarray.array([ObjectName('com.bea:Name=app-cluster,Type=Cluster')], ObjectName))
activate()

startEdit()
cd('/')
cmo.createPartition('dp1')
cd('/Partitions/dp1/SystemFileSystem/dp1')
cmo.setRoot('/u01/wins/wls1221/user_projects/domains/base_domain/partitions/dp1/system')
cmo.setCreateOnDemand(true)
cmo.setPreserved(true)
cd('/Partitions/dp1')
set('AvailableTargets',jarray.array([ObjectName('com.bea:Name=VT1,Type=VirtualTarget')], ObjectName))
set('DefaultTargets',jarray.array([ObjectName('com.bea:Name=VT1,Type=VirtualTarget')], ObjectName))
activate()

startEdit()
cmo.createResourceGroup('app1RG')
cd('/Partitions/dp1/ResourceGroups/app1RG')
cmo.setUseDefaultTarget(false)
set('Targets',jarray.array([ObjectName('com.bea:Name=VT1,Type=VirtualTarget')], ObjectName))
activate()



startEdit()
deploy(appName='conference', partition='dp1', resourceGroup='app1RG', path='/u01/content/weblogic-innovation-seminars/WInS_Demos/MT-Workshop/JCS/ConferencePlanner.war')
activate()

startEdit()
cd('/')
partitionBean=cmo.lookupPartition('dp1')
startPartitionWait(partitionBean)
activate()


print "************************ Creating JDBC datasource in app1RG resource group ****************************"
startEdit()

cd('/Partitions/dp1/ResourceGroups/app1RG')
cmo.createJDBCSystemResource('cp')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp')
cmo.setName('cp')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp/JDBCDataSourceParams/cp')
set('JNDINames',jarray.array([String('jdbc/cp')], String))

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp')
cmo.setDatasourceType('GENERIC')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp/JDBCDriverParams/cp')
cmo.setUrl('jdbc:oracle:thin:@//localhost:1521/pdborcl')
cmo.setDriverName('oracle.jdbc.xa.client.OracleXADataSource')
cmo.setPassword('conference')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp/JDBCConnectionPoolParams/cp')
cmo.setTestTableName('SQL ISVALID\r\n\r\n\r\n')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp/JDBCDriverParams/cp/Properties/cp')
cmo.createProperty('user')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp/JDBCDriverParams/cp/Properties/cp/Properties/user')
cmo.setValue('conference')

cd('/Partitions/dp1/ResourceGroups/app1RG/JDBCSystemResources/cp/JDBCResource/cp/JDBCDataSourceParams/cp')
cmo.setGlobalTransactionsProtocol('TwoPhaseCommit')

activate()
disconnect()


