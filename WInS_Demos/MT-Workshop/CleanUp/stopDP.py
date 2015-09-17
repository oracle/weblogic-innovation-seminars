connect('weblogic','welcome1','t3://localhost:7001')
edit()
startEdit()

cd('/')
partitionBeanDP1=cmo.lookupPartition('Medrec-Dev')
forceShutdownPartitionWait(partitionBeanDP1)
partitionBeanDP2=cmo.lookupPartition('dp2')
forceShutdownPartitionWait(partitionBeanDP2)
partitionBeanDP3=cmo.lookupPartition('dp3')
forceShutdownPartitionWait(partitionBeanDP3)
activate()
startEdit()
editService.getConfigurationManager().removeReferencesToBean(getMBean('/Partitions/dp2'))
cmo.destroyPartition(getMBean('/Partitions/dp2'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Partitions/dp3'))
cmo.destroyPartition(getMBean('/Partitions/dp3'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Partitions/Medrec-Dev'))
cmo.destroyPartition(getMBean('/Partitions/Medrec-Dev'))

activate()

startEdit()
cmo.destroyVirtualTarget(getMBean('/VirtualTargets/VT-daytrader'))
cmo.destroyVirtualTarget(getMBean('/VirtualTargets/VT-Medrec-1'))
cmo.destroyVirtualTarget(getMBean('/VirtualTargets/VT-Medrec-2'))
activate()

startEdit()
shutdown('app-cluster-1','Server','t3://locahost:7001')
shutdown('app-cluster-2','Server','t3://locahost:7001')
activate()


startEdit()
editService.getConfigurationManager().removeReferencesToBean(getMBean('/Clusters/app-cluster'))
cmo.destroyCluster(getMBean('/Clusters/app-cluster'))
activate()
disconnect()





