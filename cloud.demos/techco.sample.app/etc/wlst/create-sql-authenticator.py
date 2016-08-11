# variables
username = ''
password = ''
URL='t3://localhost:9002'
domain='TechCoDomain'
realm='myrealm'
realm_path='/SecurityConfiguration/'+domain+'/Realms/'+realm
datasourceName='OE'

# Connect and Activate Edit Mode
connect(username,password,URL)
edit()
startEdit()

# Create Authenticator
cd(realm_path)
cmo.createAuthenticationProvider('OEAuthenticator', 'weblogic.security.providers.authentication.SQLAuthenticator')

cd(realm_path+'/AuthenticationProviders/OEAuthenticator')
cmo.setControlFlag('SUFFICIENT')
cmo.setPlaintextPasswordsEnabled(true)
cmo.setPasswordStyle('SALTEDHASHED')

cmo.setSQLListGroups('SELECT SG_NAME FROM SECURITY_GROUPS WHERE SG_NAME like ?')
cmo.setDescriptionsSupported(false)
cmo.setDataSourceName(datasourceName)
cmo.setSQLListMemberGroups('SELECT sg.sg_name FROM Customers c,Security_Groups sg, Customers_Group cg WHERE c.customer_id=cg.csg_cust_id and cg.csg_sg_id=sg.sg_id and c.cust_email=?')
cmo.setSQLUserExists('SELECT CUSTOMERS.CUST_EMAIL FROM  CUSTOMERS where CUSTOMERS.CUST_EMAIL=?')
cmo.setSQLIsMember('SELECT c.cust_email FROM Customers c,Security_Groups sg, Customers_Group cg WHERE c.customer_id=cg.csg_cust_id and cg.csg_sg_id=sg.sg_id AND sg.sg_name=? and c.cust_email=?')
cmo.setSQLGetUsersPassword('SELECT CUSTOMER_PASSWORD.CUSTPWD_PASSWORD FROM CUSTOMER_PASSWORD, CUSTOMERS WHERE CUSTOMERS.CUSTOMER_ID=CUSTOMER_PASSWORD.CUSTPWD_CUSTID and CUSTOMERS.CUST_EMAIL= ?')
cmo.setSQLGroupExists('SELECT SG_NAME FROM SECURITY_GROUPS WHERE SG_NAME = ?')
cmo.setSQLSetUserPassword('UPDATE CUSTOMER_PASSWORD SET CUSTOMER_PASSWORD.CUSTPWD_PASSWORD = ? WHERE CUSTOMER_PASSWORD.CUSTPWD_CUSTID = (SELECT CUSTOMERS.CUSTOMER_ID FROM  CUSTOMERS where CUSTOMERS.CUST_EMAIL=?)')
cmo.setSQLListGroupMembers('SELECT c.cust_email FROM Customers c,Security_Groups sg, Customers_Group cg WHERE c.customer_id=cg.csg_cust_id and cg.csg_sg_id=sg.sg_id AND sg.sg_name=? and c.cust_email like ?')
cmo.setSQLListUsers('SELECT CUSTOMERS.CUST_EMAIL FROM  CUSTOMERS where CUSTOMERS.CUST_EMAIL LIKE ?')
cmo.setSQLRemoveGroupMemberships('')
cmo.setSQLRemoveUser('')
cmo.setSQLAddMemberToGroup('')
cmo.setSQLCreateGroup('')
cmo.setSQLGetGroupDescription('')
cmo.setSQLCreateUser('')
cmo.setSQLRemoveGroup('')
cmo.setSQLRemoveMemberFromGroup('')
cmo.setSQLSetUserDescription('')
cmo.setSQLRemoveGroupMember('')
cmo.setSQLSetGroupDescription('')
cmo.setSQLGetUserDescription('')

activate()
startEdit()

cd(realm_path)
set('AuthenticationProviders',jarray.array([ObjectName('Security:Name=myrealmTrust Service Identity Asserter'), ObjectName('Security:Name=myrealmDefaultAuthenticator'), ObjectName('Security:Name=myrealmOEAuthenticator'), ObjectName('Security:Name=myrealmDefaultIdentityAsserter')], ObjectName))
dumpStack()

activate()
startEdit()

cd(realm_path+'/AuthenticationProviders/DefaultAuthenticator')
cmo.setControlFlag('SUFFICIENT')

activate()

dumpStack()
exit()