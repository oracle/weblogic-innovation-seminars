loadProperties('ops-domain.properties')

connect(adminServer_Username, adminServer_Password, 't3://' + adminServer_ListenAddress + ':' + adminServer_ListenPort)

edit()
startEdit()

progress = deploy(appName='weblogic-spring', path=weblogic_home_path + '/server/lib/weblogic-spring.jar',
    targets=cluster_Name, libraryModule='true')
progress.printStatus()
save()
activate(block="true")
disconnect()
exit()
