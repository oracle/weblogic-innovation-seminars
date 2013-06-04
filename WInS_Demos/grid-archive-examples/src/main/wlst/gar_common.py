#
# Name:      gar_common.py
# Author:    (Oracle)
# Date:      2012.10.26
# Purpose:   Deployment of a Grid Archive (GAR) may involve concurrent deployments across
#            many managed servers. If the GAR utilizes the Coherence Partitioned Service 
#            deployment must be carried out in a staged fashion to ensure no partition loss 
#            occurs. This WLST script provides a function to alleviate this concern ensuring 
#            partitions are 'safe' during redeployment. Placing this file in 
#            $WL_HOME/common/wlst will ensure its inclusion in WLST scripts.
#
# Usage:     garRedeploy(appName, appFile, clusterName, safetyMode, timeout, shutdownRestart, upload ):
#            First 3 arguments are mandatory and others have defaults.
#
# Arguments: appName         - applicaiton name           - e.g. ExampleGAR
#            appFile         - new application file       - e.g. /home/coherence/ExampleGAR.gar
#            clusterName     - WebLogic Cluster Name
#            safetyMode      - safety mode (optional)     - NODE-SAFE, MACHINE-SAFE, RACK-SAFE, SITE-SAFE (default NODE-SAFE)
#            timeout         - Timeout (optional)         - Time to wait for above safefy mode (default 60 seconds)
#            shutdownRestart - shutdownrestart (optional) - e.g. indicates if to stop managed server before deploy 
#                                                           and then restart after deploy. Nodemanaged must be setup.
#            upload          - upload                     - if the original deploy used upload=true, this must be set.
#
# Note:      The absolute path to the file name should be the same as the deployed file name otherwise
#            the deployment name will have a "-1" appended.

import thread

# statusHA values
# Numerical values are used to represent the following statusHA values from the array:
# 4 - SITE-SAFE
# 3 - RACK-SAFE
# 2 - MACHINE-SAFE
# 1 - NODE-SAFE
# 0 - ENDANGERED
statusHAValues = [ 'ENDANGERED', 'NODE-SAFE', 'MACHINE-SAFE', 'RACK-SAFE', 'SITE-SAFE' ]

# Purpose:   Return the index value (strength) for the given statusHA value
#
# Arguments: statusHAValue - the statusHA value to check
#
# Returns:   The index of the statusHA value in the array
#
def __getStatusHAIndex(statusHAValue):
   i = -1
   for value in statusHAValues:
      i = i + 1
      if (statusHAValue == value):
         return i
   return -1

# Purpose:   Return the StatusHA value based upon the service prefix specified.
#
# Arguments: servicePrefix - the service prefix to check. This is used in the MBean query
#
# Returns:   The current statusHA value as determined by the members that match the MBean query
#
def __checkStatusHA(servicePrefix):
   domainRuntime()
   query = 'Coherence:type=Service,name="' + servicePrefix + ':*",*'
   beans = list(mbs.queryMBeans(ObjectName(query),None))

   checkStatusHA = 4   # SITE-SAFE - highest level

   # statusHA will be the least strong of all statusHA values.
   for service in beans:
      objectName = service.getObjectName()
      statusHA = mbs.getAttribute(objectName,"StatusHA")
      if statusHA != 'n/a':
         # now check the status HA
         statusHAIndex = __getStatusHAIndex(statusHA)

         if statusHAIndex == 0:  # ENDANGERED, return immediately
            return statusHA
         
         # If the current statusHA index < existing then set it to current
         if statusHAIndex < checkStatusHA:
            checkStatusHA = statusHAIndex
   
   return statusHAValues[checkStatusHA]

#
# Purpose:    Check the status HA value passed in against the safetyMode Required.
#
# Agruments:  currentStatusHA - the current statusHA value obtained from a call to checkStatusHA
#             safetyMode      - the required safety mode of NODE-SAFE or MACHINE-SAFE
#
# Returns:    a value of OK otherwise a messaging indicating statusHA is no good
#
def __evaluateStatusHA(currentStatusHA, safetyMode):

   currentStatusHAIndex = __getStatusHAIndex(currentStatusHA)
   safetyModeIndex      = __getStatusHAIndex(safetyMode)

   # If the current status >= safetymode then all is ok.
   # e.g. If we have specified NODE-SAFE safety mode (2), and we currently have
   #      MACHINE-SAFE (3), then this is OK, otherwise return a message indicating
   #      a problem. 

   if currentStatusHAIndex >= safetyModeIndex:
      return 'OK'

   return 'Cluster service is ' + currentStatusHA + ' and not at least ' + safetyMode + '. Cannot continue.'

#
# Purpose:     Wait for statusValue that is acceptable.
#
# Arguments    servicePrefix  - the service name prefix to check
#              safetyMode     - the required safety mode of NODE-SAFE or MACHINE-SAFE
#              timeout        - the number of seconds to wait for the safety mode
#
def __waitForGoodStatusHA(servicePrefix, safetyMode, timeout):
   sleepTime = 5
   maxSleepTime = timeout
   totalSleepTime = 0
   result = 'Not Checked'

   while result != 'OK':
      print 'Checking cluster statusHA. Timeout is ' + str(maxSleepTime) + ' seconds'
      currentStatusHA = __checkStatusHA(servicePrefix)
      result          = __evaluateStatusHA(currentStatusHA, safetyMode)
      #print 'result is ' + result
      if result != 'OK':
         print result + ' Sleeping for ' + str(sleepTime) + ' seconds. Total time waited is ' + str(totalSleepTime) + ' seconds.'
         java.lang.Thread.sleep(sleepTime * 1000)
         totalSleepTime += sleepTime
         if totalSleepTime >= maxSleepTime:
            print 'Waited max time of ' + str(maxSleepTime) + ' for statusHA to be good.'
            print 'Cannot continue. You will need to check log to manually deploy applications that did not deploy.'
            exit(exitcode=6)
         # sleep

   print 'Cluster service is ' + currentStatusHA + ' which matches required value. '
   print ' '

##
## Actual function to carry out rolling restart/redeplpoy.
## First three parameters are mandatory. See above for description of parameters.
##

def garRedeploy(appName, appFile, clusterName, safetyMode = 'NODE-SAFE', timeout=60, shutdownRestart='false', upload='false'):
   print 'Defaulting timeout to 60 seconds, shutdownRestart to false and upload=false'

   weblogicClusterName = clusterName
   safetyMode          = safetyMode
   appName             = appName   
   appFile             = appFile
   servicePrefix       = appName
   shutdownRestart     = shutdownRestart
   waitTimeout         = timeout
   uploadFile          = upload

   if __getStatusHAIndex(safetyMode) < 1:
      print 'safetyMode must be either SITE-SAFE, RACK-SAFE, MACHINE-SAFE NODE-SAFE'
      exit(exitcode=1)

   # Check for existence of the gar file specified
   try:
      f = open(appFile)
   except:
      print 'Cannot find file ' + appFile + ', unable to continue.'
      exit(exitcode=2)

   # get the cluster
   if getMBean('/Clusters/' + weblogicClusterName) == None:
      print weblogicClusterName, ' Cluster does not exist'
      exit(exitcode=8)

   currentStatusHA = __checkStatusHA(servicePrefix)
   result = __evaluateStatusHA(currentStatusHA, safetyMode)
   if result != 'OK':
      print result
      exit(exitcode=1)

   print 'Current status HA of ' + currentStatusHA + ' is OK'

   # 
   # What we do is:
   # - go through each server in the cluster
   # - re-deploy the application with the specifiedTargetsOnly=true option
   # - wait for acceptable statusHA
   # 
   # Note: Currently the application must be deployed directly to the servers, otherwise
   #       This script will fail.

   serverConfig()
   cd ('/Clusters/' + weblogicClusterName)
   servers = cmo.getServers()
   totalServers = len(servers)

   print 'Total number of servers in cluster ' + weblogicClusterName + ' is ' + str(totalServers)

   totalCompleted = 0
   for server in servers:
      currentServerName = server.getName()

      __waitForGoodStatusHA(servicePrefix, safetyMode, waitTimeout)
      
      # if we get here we are ok as statusHA is good so lets deploy to the server
      print 'Deploying ' + appName + ' using ' + appFile + ' to server ' + currentServerName + ' with option specifiedTargetsOnly=true'

      # You must use  specifiedTargetsOnly='true' otherwise the GAR will be undeployed
      # from all managed servers in the cluster at the same time. This would mean data loss.
      #
      if shutdownRestart == 'true':
	 shutdown(currentServerName)

      deploy(appName, appFile, currentServerName, specifiedTargetsOnly='true', upload=uploadFile)

      if shutdownRestart == 'true':
	 start(currentServerName)

      print 'Deployment finished. Sleeping 5.'

      # now that its deployed, wait until status HA is ok and them move on
      __waitForGoodStatusHA(servicePrefix, safetyMode, waitTimeout)

      totalCompleted +=1

   # now if we get here and totalCompleted != totalServers then there is a problem
   if totalCompleted != totalServers:
      print 'Only ' + str(totalCompleted) + ' servers completed out of ' + str(totalServers)
      print 'Please check logs for errors.'
   else:
      print 'Application deployed over all ' + str(totalServers) + ' servers'

   print 'Exiting'
