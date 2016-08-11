# variables
username = ''
password = ''
URL='t3://129.152.150.19:9001'


# Connect and Activate Edit Mode
connect(username,password,URL)



serverConfig()

print 'lookup DefaultAuthenticator' 

password = 'welcome1'

atnr=cmo.getSecurityConfiguration().getDefaultRealm().lookupAuthenticationProvider('DefaultAuthenticator')

group = 'Temp-Group'
atnr.createGroup(group,group)

users = ['user1', 'user2']

for user in users : 
  print 'create user: ', user
  atnr.createUser(user,password, user)
  atnr.addMemberToGroup(group, user)
  



