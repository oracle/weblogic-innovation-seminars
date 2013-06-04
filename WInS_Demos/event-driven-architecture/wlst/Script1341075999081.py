cd('/')
cmo.createFileStore('FileStore-0')

cd('/FileStores/FileStore-0')
set('Targets', jarray.array([ObjectName('com.bea:Name=ms-1 (migratable),Type=MigratableTarget')], ObjectName))

cd('/')
cmo.destroyFileStore(getMBean('/FileStores/FileStore-0'))
cmo.createFileStore('FileStore-0')

cd('/FileStores/FileStore-0')
cmo.setDirectory('/u01/shared/jms-file-store-1')
set('Targets', jarray.array([ObjectName('com.bea:Name=ms-1 (migratable),Type=MigratableTarget')], ObjectName))
