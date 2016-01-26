#Connecting to NodeManager
nmConnect('weblogic', 'welcome1', domainName='Domain1221', port='5557', host='localhost')

#Setting Arguments for AdminServer
adminArgs='-Dweblogic.log.StdoutSeverity=Debug -Dweblogic.security.SSL.ignoreHostnameVerification=true -Dweblogic.debug.DebugPatchingRuntime=true -Dweblogic.log.StdoutSeverity=Debug -Dweblogic.log.LogSeverity=Debug -Dweblogic.log.LoggerSeverity=Debug -Djava.security.egd=file:/dev/./urandom'

#Creating Properties parameter for AdminServer
prps = makePropertiesObject('')
prps.setProperty('username', 'weblogic')
prps.setProperty('password', 'welcome1')
prps.setProperty('Arguments', adminArgs)

#Starting AdminServer
nmStart('AdminServer', props=prps)
exit()
