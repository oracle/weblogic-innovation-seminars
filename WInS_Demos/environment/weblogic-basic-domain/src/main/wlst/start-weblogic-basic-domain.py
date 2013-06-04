import time

loadProperties('environment.properties')

DOMAIN_NAME = 'weblogic-basic-domain'

adminServer_ListenAddress = 'wins-vbox'
adminServer_ListenPort = 9001
adminServer_AdministrationPort = 9200
adminServer_Username = 'weblogic'
adminServer_Password = 'welcome1'

adminURL='t3://' + adminServer_ListenAddress + ':' + str(adminServer_ListenPort)

cluster_name = 'cluster-1'

managed_server_count = 2
managed_server_name_base = 'ms'

listen_address = 'wins-vbox.localdomain'
var_domain_dir = USER_PROJECTS + '/domains/' + DOMAIN_NAME

coh_server_count = 2

########################################

def startCoherenceServers():
    for n in range(1, int(coh_server_count) + 1):
        coherenceServerName = 'coh-' + str(n)

        lifecycle = getMBean('/CoherenceServerLifeCycleRuntimes/' + coherenceServerName)

        try:
            print("Starting Coherence Server: " + coherenceServerName)
            lifecycle.start()
            time.sleep(5000)
            print("Cohernece Server Status:" + lifecycle.getState())

        except:
            print '============================================='
            print 'Unable to start Coherence server: [' + coherenceServerName + ']'
            print '============================================='
            print ''
            print("Cohernece Server Status:" + lifecycle.getState())
            dumpStack()

########################################


def startManagedServers():
    for n in range(1, int(managed_server_count) + 1):
        managedServerName = managed_server_name_base + '-' + str(n)

        try:
            print 'Starting Managed Server: ' + managedServerName
            start(managedServerName, 'Server')
        except:
            print '============================================='
            print 'Unable to start Managed server: [' + managedServerName + ']'
            print '============================================='
            print ''
            dumpStack()

########################################

def startAdminServer():
    try:
        nmStart('AdminServer')
    except:
        print '============================================='
        print 'Unable to start Admin Server!!!'
        print '============================================='
        print ''
        dumpStack()

########################################
def startCluster():

    try:
        start(cluster_name, 'Cluster')
    except:
        print '============================================='
        print 'Unable to start Cluster!!!'
        print '============================================='
        print ''
        dumpStack()

########################################


print ''
print '============================================='
print 'Connecting to Node Manager...'
print '============================================='
print ''

nmConnect(adminServer_Username, adminServer_Password, listen_address, 5556, DOMAIN_NAME, var_domain_dir, 'plain')

print ''
print '============================================='
print 'Connected to NODE MANAGER Successfully'
print '============================================='
print ''

startAdminServer()
