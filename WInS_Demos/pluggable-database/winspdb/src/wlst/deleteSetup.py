connect('weblogic','welcome1','t3://wins-vbox:9001')
edit()
startEdit()

cd('/')
cmo.destroyJDBCSystemResource(getMBean('/JDBCSystemResources/pdbds'))

activate()

undeploy('winspdb')
