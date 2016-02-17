# This is the start of the script
connect('weblogic','welcome1','t3://localhost:8001')

edit()

startEdit()
cd('/')
cmo.createUnixMachine('otd_machine')
cd('/Machines/otd_machine/NodeManager/otd_machine')
cmo.setNMType('SSL')
cmo.setListenAddress('localhost')
cmo.setListenPort(5557)
activate()

startEdit()
otd_createConfiguration({'configuration':'mt','server-name':'localhost','listener-port':'8080'})
otd_createInstance({'configuration': 'mt', 'machine': "otd_machine"})
activate()

startEdit()
start("otd_mt_otd_machine")
activate()

disconnect()

connect('weblogic','welcome1','t3://localhost:7001')
edit()

startEdit()
cd('/')
cmo.createVirtualTarget('VT-1')
cd('/VirtualTargets/VT-1')
set('HostNames',jarray.array([String('localhost')], String))
set('Targets',jarray.array([ObjectName('com.bea:Name=app-cluster,Type=Cluster')], ObjectName))
cmo.setUriPrefix('/dp4')
activate()

disconnect()
