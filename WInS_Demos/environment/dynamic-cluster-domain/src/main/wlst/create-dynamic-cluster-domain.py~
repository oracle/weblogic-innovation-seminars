#loadProperties('environment.properties')

DOMAIN_NAME = 'dynamic-cluster-domain'

adminServer_Username = 'weblogic'
adminServer_Password = 'welcome1'
listen_address = 'localhost'
USER_PROJECTS='/u01/wls1212/user_projects'
WL_HOME='/u01/wls1212/wlserver'


############################################################################################################################################
#BEGIN MAIN

var_domain_dir = USER_PROJECTS + '/domains/' + DOMAIN_NAME
print 'Creating domain in path=' + var_domain_dir

try:
    createDomain(WL_HOME + '/common/templates/wls/wls.jar', var_domain_dir, adminServer_Username, adminServer_Password)
    print 'domain created'

    readDomain(var_domain_dir)
    print 'read domain'

    cd('/')
    cmo.setExalogicOptimizationsEnabled(false)
    cmo.setClusterConstraintsEnabled(false)
    cmo.setGuardianEnabled(false)
    cmo.setAdministrationPortEnabled(false)
    cmo.setConsoleEnabled(true)
    cmo.setConsoleExtensionDirectory('console-ext')
    cmo.setProductionModeEnabled(false)
    cmo.setAdministrationProtocol('t3s')
    cmo.setConfigBackupEnabled(false)
    cmo.setConfigurationAuditType('none')
    cmo.setInternalAppsDeployOnDemandEnabled(false)
    cmo.setConsoleContextPath('console')

    cd('/Servers/AdminServer')
    cmo.setListenPortEnabled(true)
    cmo.setAdministrationPort(int(adminServer_AdministrationPort))
    cmo.setListenPort(int(adminServer_ListenPort))
    cmo.setWeblogicPluginEnabled(false)
    cmo.setJavaCompiler('javac')
    cmo.setStartupMode('RUNNING')
    cmo.setVirtualMachineName(DOMAIN_NAME + '_AdminServer')
    cmo.setClientCertProxyEnabled(false)
except:
    dumpStack()
    exit()

