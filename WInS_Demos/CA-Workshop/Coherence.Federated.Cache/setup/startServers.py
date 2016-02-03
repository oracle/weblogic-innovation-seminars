#Connectiong to NodeManager in A_site_domain to Start AdminServer,cache_server1,webapp_server1
nmConnect('weblogic','welcome1',domainName='A_site_domain',port='5556',host='localhost')
nmStart('cache_server1')
nmStart('webapp_server1')
nmDisconnect()

#Connectiong to NodeManager in B_site_domain to Start AdminServer,cache_server1,webapp_server1
nmConnect('weblogic','welcome1',domainName='B_site_domain',port='5559',host='localhost')
nmStart('cache_server1')
nmStart('webapp_server1')
nmDisconnect()

