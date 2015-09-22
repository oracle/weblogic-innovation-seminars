
nmConnect('weblogic','welcome1','localhost',5556,'base_domain','/u01/wins/wls1221/user_projects/domains/base_domain','SSL')
nmStart('AdminServer')
connect('weblogic','welcome1','t3://localhost:7001')
start('app-cluster','Cluster')
disconnect()
exit()





