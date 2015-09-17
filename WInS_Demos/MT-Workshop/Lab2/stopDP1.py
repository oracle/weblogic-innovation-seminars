connect('weblogic','welcome1','t3://localhost:7001')
edit()
startEdit()
cd('/')
partitionBeanDP1=cmo.lookupPartition('dp1')
forceShutdownPartitionWait(partitionBeanDP1)
activate()

startEdit()
editService.getConfigurationManager().removeReferencesToBean(getMBean('/Partitions/dp1'))
cmo.destroyPartition(getMBean('/Partitions/dp1'))
activate()
disconnect()





