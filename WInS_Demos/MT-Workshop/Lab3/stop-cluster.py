connect('weblogic','welcome1','t3://localhost:7001')
edit()
startEdit()
shutdown('app-cluster','Cluster','true',1000,force='true')
activate()
disconnect()

