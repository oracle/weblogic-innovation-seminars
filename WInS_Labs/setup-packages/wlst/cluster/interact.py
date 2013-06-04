import time

loadProperties('weblogic-examples-domain.properties')

var_domain_dir=user_projects_path+'/domains/'+domain_name

readDomain(var_domain_dir)
print 'read domain'
