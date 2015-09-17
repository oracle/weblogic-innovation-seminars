connect('weblogic','welcome1','t3://localhost:7001')
edit()
startEdit()
cd('/')
partitionBeanDP1=cmo.lookupPartition('Medrec-Dev')
forceShutdownPartitionWait(partitionBeanDP1)
activate()
disconnect()





