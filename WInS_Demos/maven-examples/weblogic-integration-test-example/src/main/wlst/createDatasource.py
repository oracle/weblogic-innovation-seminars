#Begin Script

var_username = 'weblogic'
var_password = 'welcome1'

connect(var_username, var_password, 't3://127.0.0.1:7001')
edit()
startEdit()

cd('/')
cmo.createJDBCSystemResource('xe-datasource')

cd('/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource')
cmo.setName('xe-datasource')

cd('/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource/JDBCDataSourceParams/xe-datasource')
set('JNDINames', jarray.array([String('cgDatasource')], String))

cd('/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource/JDBCDriverParams/xe-datasource')
cmo.setUrl('jdbc:oracle:thin:@wins-vbox.localdomain:1521:xe')
cmo.setDriverName('oracle.jdbc.OracleDriver')
setEncrypted('Password', 'Password_1326590775531', 'src/main/wlst/Script1326590585284Config',
    'src/main/wlst/Script1326590585284Secret')

cd('/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource/JDBCConnectionPoolParams/xe-datasource')
cmo.setTestTableName('SQL SELECT 1 FROM DUAL\r\n\r\n\r\n')

cd(
    '/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource/JDBCDriverParams/xe-datasource/Properties/xe-datasource')
cmo.createProperty('user')

cd(
    '/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource/JDBCDriverParams/xe-datasource/Properties/xe-datasource/Properties/user')
cmo.setValue('ops')

cd('/JDBCSystemResources/xe-datasource/JDBCResource/xe-datasource/JDBCDataSourceParams/xe-datasource')
cmo.setGlobalTransactionsProtocol('None')

cd('/JDBCSystemResources/xe-datasource')
set('Targets', jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

activate()


