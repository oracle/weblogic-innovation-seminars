loadProperties('weblogic-examples-domain.properties')

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

cd('/')
cmo.createJDBCSystemResource('cluster-ds-nonXA')

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA')
cmo.setName('cluster-ds-nonXA')

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA/JDBCDataSourceParams/cluster-ds-nonXA')
set('JNDINames',jarray.array([String('jdbc/ds/cluster')], String))

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA/JDBCDriverParams/cluster-ds-nonXA')
cmo.setUrl(database_URL)
cmo.setDriverName(database_driver)
cmo.setPassword(database_password)

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA/JDBCConnectionPoolParams/cluster-ds-nonXA')
cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n')

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA/JDBCDriverParams/cluster-ds-nonXA/Properties/cluster-ds-nonXA')
cmo.createProperty('user')

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA/JDBCDriverParams/cluster-ds-nonXA/Properties/cluster-ds-nonXA/Properties/user')
cmo.setValue(database_user)

cd('/JDBCSystemResources/cluster-ds-nonXA/JDBCResource/cluster-ds-nonXA/JDBCDataSourceParams/cluster-ds-nonXA')
cmo.setGlobalTransactionsProtocol('None')

cd('/JDBCSystemResources/cluster-ds-nonXA')
set('Targets',jarray.array([ObjectName('com.bea:Name='+cluster_Name+',Type=Cluster')], ObjectName))

save()
activate(block="true")
disconnect()
exit()
