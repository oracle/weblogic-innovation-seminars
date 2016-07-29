# ORACLE Public Java Cloud Service tutorial

## Introduction ##

Oracle Java Cloud Service is a part of the platform service offerings in Oracle Cloud. Powered by Oracle WebLogic Server, it provides a platform on top of Oracle's enterprise-grade cloud infrastructure for developing and deploying new or existing Java EE applications.

### About this tutorial ###
This tutorial demonstrates basic functionality of Oracle Java Cloud Service:

1. Create storage for Java and Database Cloud Service using REST API of Oracle Storage Cloud Service. *(Oracle Java and Database Cloud Service uses Oracle Storage Cloud Service containers to store service backups.)*
2. Create Java and Database Cloud Service instances from the Service Console using the Provision New Database/Java Cloud Service wizard. *(Each Oracle Java Cloud Service instance must be associated with an Oracle Database Cloud - Database as a Service database deployment. Oracle Java Cloud Service relies on Oracle Database Cloud - Database as a Service to host the schemas required by Oracle JRF.)*
3. Accessing the Oracle Java Cloud Service VM Through a Secure Shell (SSH). Copy demo application to the VM.
4. Deploy sample application to Oracle Java Cloud Service.
5. View log file directly in the VM.
6. Scale out Oracle Java Cloud Service instance using Service Console
7. Get the psm (PaaS Service Manager) tool
8. Scale down Oracle Java Cloud Service instance using psm tool

### Pre-requsite ###
- Access to INTERNET.
- Oracle Public Cloud Service account which has Java and Database Could Service subscription.

----------
## Create storage container ##
A container is a storage compartment that provides a way to organize the data stored in Oracle Storage Cloud Service.

NOTE: Before you create your first container, make sure that the replication policy has been set for your account. See [Selecting a Replication Policy for Oracle Storage Cloud Service](https://docs.oracle.com/cloud/latest/storagecs_common/CSSTO/GUID-5D53C11F-3D9E-43E4-8D1D-DDBB95DEC715.htm).

This step uses [REST API of Oracle Storage Cloud Service](http://docs.oracle.com/cloud/latest/storagecs_common/SSAPI/index.html). To invoke HTTP REST call now use [curl](https://curl.haxx.se/). In case of most of the Linux distribution curl is already included. But in case of Windows it needs to be download and install. This tutorial doesn't cover curl installation.

##### Determine the storage REST endpoint URL #####
1. Open a browser and go to [cloud.oracle.com](https://cloud.oracle.com). Select the datacenter and click on My Services.

2. Enter the identity domain click on Go.

3. Enter username and password of user with Service Administrator role.

4. On the Dashboard click on Storage link.

5. On the storage service detail page note the **REST Endpoint** URL, which is displayed in the REST Endpoint field. 
For example: `https://myIdentity.storage.oraclecloud.com/v1/Storage-myIdentity`
6. Delete the following portion of the REST Endpoint URL: `v1/Storage-myIdentity`
The edited URL is: `https://myIdentity.storage.oraclecloud.com/`
7. Append the following to the edited URL: `auth/v1.0`
8. Now the authentication URL would be: `https://myIdentity.storage.oraclecloud.com/auth/v1.0`

More information about storage endpoint URLs see [About REST URLs for Oracle Cloud Storage Service Resources](http://docs.oracle.com/cloud/latest/storagecs_common/CSSTO/GUID-5778ADBB-A0E8-4451-B886-362A3B7237DB.htm#CSSTO00707)
##### Get Authentication token by send the GET request to the authentication URL
1. Open terminal and Send GET request using curl to request authentication token. The command format is the following: `curl -v -X GET -H "X-Storage-User:myService-myIdentity:myUsername"  -H "X-Storage-Pass:myPassword" https://myIdentity.storage.oraclecloud.com/auth/v1.0`
Note: replace **myIdentity**, **myUsername**, **myPassword** to reflect your enviroment.
2. The following is an example of the output of this command, with certain key lines highlighted. Note that if the request includes the correct credentials, it returns the HTTP/1.1 200 OK response.

3. From the output of the command you just ran, note the value of the `X-Storage-Token` and `X-Storage-Url` header.
4. Now use the token and REST endpoint url adding the desired container name to create the container using the following curl command: `curl -v -s -X PUT -H "X-Auth-Token:AUTH_tk1538e83ac767b9074ded220c65a588a8" https://storage.us2.oraclecloud.com/v1/Storage-myIdentity/myFirstContainer`
Note: replace **AUTH_token**, **myIdentity** to reflect your enviroment.
5. Lastly verify the existence of the container named *myFirstContainer* created earlier. Run the following curl command: `curl -v -s -X GET -H "X-Auth-Token: AUTH_tk2fd283ba574f1a98edec83aa42586d74" https://storage.us2.oraclecloud.com/v1/Storage-myIdentity/opc_container` 
Note: replace **AUTH_token**, **myIdentity** to reflect your enviroment.
6. If the request is completed successfully, it returns the HTTP/1.1 204 No Content response, as shown in the following sample output. This response indicates that there are no objects yet in your new container. The X-Container-Object-Count and X-Container-Bytes-Used headers in this example have the value zero, indicating again that there are no objects in the container. As you upload data to the container, objects are created, and these headers will then show non-zero values.

For information about creating containers by using the REST API, see [Create Container in REST API for Standard Storage in Oracle Storage Cloud Service](http://docs.oracle.com/cloud/latest/storagecs_common/SSAPI/op-v1-%7Baccount%7D-%7Bcontainer%7D-put.html)