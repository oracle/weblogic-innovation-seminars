import time

loadProperties('environment.properties')

DOMAIN_NAME = 'weblogic-examples-domain'

adminServer_ListenAddress = 'wins-vbox'
adminServer_ListenPort = 7001
adminServer_AdministrationPort = 7200
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

try:
    print 'Attempting to connect to AdminServer at URL='+adminURL
    connect(adminServer_Username, adminServer_Password, adminURL)
except:
    print 'Unable to connect to AdminServer, attempting to start'
    startAdminServer()
    connect(adminServer_Username, adminServer_Password, adminURL)

disconnect()