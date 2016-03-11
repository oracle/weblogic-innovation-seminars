print "*************** Starting Admin Server in A_site_domain *****************"
nmConnect('weblogic','welcome1',domainName='A_site_domain',port='5556',host='localhost')
nmStart('AdminServer')
exit()