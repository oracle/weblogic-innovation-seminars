connect('weblogic','welcome1','t3://localhost:7001')
edit()

startEdit()
shutdown('app-cluster','Cluster','t3://locahost:7001')
shutdown('new-cluster','Cluster','t3://locahost:7001')
activate()

disconnect()

connect('weblogic','welcome1','t3://localhost:8001')
edit()

startEdit()
shutdown('otd_mt_otd_machine')
activate()

disconnect()