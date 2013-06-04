connect('weblogic','welcome1','t3://localhost:7001')
edit()
startEdit()
cd('/')
cmo.destroyJMSSystemResource(getMBean('/SystemResources/jms-module-base'))
cmo.destroyJMSServer(getMBean('/Deployments/appgrid-jms-server-1'))
cmo.destroyJMSServer(getMBean('/Deployments/appgrid-jms-server-2'))
cmo.destroyJDBCStore(getMBean('/JDBCStores/appgrid-jms-store-1'))
cmo.destroyJDBCStore(getMBean('/JDBCStores/appgrid-jms-store-1'))
cmo.destroyJDBCSystemResource(getMBean('/SystemResources/appgrid-cluster-ds-nonXA'))

cd('/Servers/appgrid-ms-1')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/appgrid-ms-1'))

cd('/')
cmo.destroyServer(getMBean('/Servers/appgrid-ms-1'))

cd('/Servers/appgrid-ms-2')
cmo.setCluster(None)
cmo.setMachine(None)

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Servers/appgrid-ms-2'))

cd('/')
cmo.destroyServer(getMBean('/Servers/appgrid-ms-2'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Clusters/appgrid-cluster-1'))
cmo.destroyCluster(getMBean('/Clusters/appgrid-cluster-1'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Machines/my-machine-1'))
cmo.destroyMachine(getMBean('/Machines/my-machine-1'))

editService.getConfigurationManager().removeReferencesToBean(getMBean('/Machines/my-machine-2'))
cmo.destroyMachine(getMBean('/Machines/my-machine-2'))


save()
activate(block="true")
disconnect()
exit()
