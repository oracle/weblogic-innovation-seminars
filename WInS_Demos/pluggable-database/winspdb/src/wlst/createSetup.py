connect('weblogic','welcome1','t3://wins-vbox:9001')
edit()
startEdit()


cd('/')
cmo.createJDBCSystemResource('pdbds')

cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds')
cmo.setName('pdbds')

cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds/JDBCDataSourceParams/pdbds')
set('JNDINames',jarray.array([String('pdbds')], String))

cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds/JDBCDriverParams/pdbds')
cmo.setUrl('jdbc:oracle:thin:@//wins-vbox:1521/ORCL')
cmo.setDriverName('oracle.jdbc.OracleDriver')
cmo.setPasswordEncrypted('welcome1')
cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds/JDBCConnectionPoolParams/pdbds')
cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n\r\n')

cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds/JDBCDriverParams/pdbds/Properties/pdbds')
cmo.createProperty('user')

cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds/JDBCDriverParams/pdbds/Properties/pdbds/Properties/user')
cmo.setValue('c##cdbuser')

cd('/JDBCSystemResources/pdbds/JDBCResource/pdbds/JDBCDataSourceParams/pdbds')
cmo.setGlobalTransactionsProtocol('OnePhaseCommit')

cd('/JDBCSystemResources/pdbds')
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

activate()


deploy('winspdb','/u01/content/weblogic-innovation-seminars/WInS_Demos/pluggable-database/src/build/winspdb.war',targets='AdminServer')
startApplication('winspdb')


