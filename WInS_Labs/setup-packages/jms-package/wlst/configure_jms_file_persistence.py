loadProperties('../../wlst/cluster/weblogic-examples-domain.properties')
connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)
edit()
startEdit()

cd('/')
cmo.createFileStore('jms-file-store-1')
cd('/FileStores/jms-file-store-1')
set('Targets',jarray.array([ObjectName('com.bea:Name='+MS1_Name+' (migratable),Type=MigratableTarget')], ObjectName))
cmo.setDirectory('/u01/jms-store-1')

cd('/')
cmo.createFileStore('jms-file-store-2')
cd('/FileStores/jms-file-store-2')
cmo.setDirectory('/u01/jms-file-store-2')
set('Targets',jarray.array([ObjectName('com.bea:Name='+MS2_Name+' (migratable),Type=MigratableTarget')], ObjectName))

cd('/JMSServers/jms-server-1')
cmo.setPersistentStore(getMBean('/FileStores/jms-file-store-1'))
set('Targets',jarray.array([ObjectName('com.bea:Name='+MS1_Name+' (migratable),Type=MigratableTarget')], ObjectName))

cd('/JMSServers/jms-server-2')
set('Targets',jarray.array([ObjectName('com.bea:Name='+MS2_Name+' (migratable),Type=MigratableTarget')], ObjectName))
cmo.setPersistentStore(getMBean('/FileStores/jms-file-store-2'))

save()
activate(block="true")
disconnect()
exit()