package com.oracle.wins.restclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.oracle.wins.util.restclient.util.OPCProperties;

public class ExecuteGoal {

	public static void main(String[] args) {
		
		OPCProperties opcProperties;
				
		String response = "";
		StringBuffer sbTemp = null;
		
		String sGoal = "";
		String sJobId = "";
		
		if (args.length < 2) {
			System.out.println("Not enough parameters defined!");
		} else {
			
			opcProperties = OPCProperties.getInstance();
			opcProperties.init(args[0]);
			
			System.out.println("Selected goal: " + args[1]);
			
			sGoal = args[1];
			
			if (args.length > 2 && args[2] != null) {
				sJobId = args[2];
			}
			
			Credentials credOPCUser = null;
			BasicNameValuePair[] aHeaders = null;
			String sUri = null;
			Properties storageProperties = null;
			
			switch (sGoal.toLowerCase()) {
	        case OPCProperties.GOAL_JCS_GET_INSTANCE_DETAILS:
	        	System.out.println("JCS get specific instance details----------------------------------------");
	
	        	aHeaders = new BasicNameValuePair[2];
	        	
	        	aHeaders[0] = new BasicNameValuePair("accept", OPCProperties.CONTENT_TYPE_JSON);
	        	aHeaders[1] = new BasicNameValuePair("X-ID-TENANT-NAME", opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	
				sUri = "http://" + opcProperties.getProperty(OPCProperties.OPC_BASE_URL) 
						+ opcProperties.getProperty(OPCProperties.JCS_REST_URL)
						+ opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN)
						+ "/" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_1);
	        	
				credOPCUser = new UsernamePasswordCredentials(opcProperties.getProperty(OPCProperties.OPC_USERNAME), opcProperties.getProperty(OPCProperties.OPC_PASSWORD));
				
	    		response = ApacheHttpClientGet.httpClientGET(sUri, aHeaders, null, credOPCUser);
		        
	        	System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	
	        	break;
	        case OPCProperties.GOAL_DBCS_GET_INSTANCE_DETAILS:
	        	System.out.println("DBCS get specific instance details----------------------------------------");
	        	
        		aHeaders = new BasicNameValuePair[2];
	        	
	        	aHeaders[0] = new BasicNameValuePair("accept", OPCProperties.CONTENT_TYPE_JSON);
	        	aHeaders[1] = new BasicNameValuePair("X-ID-TENANT-NAME", opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	
				sUri = "http://" + opcProperties.getProperty(OPCProperties.OPC_BASE_URL) 
						+ opcProperties.getProperty(OPCProperties.DBCS_REST_URL)
						+ opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN)
						+ "/" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_1);
	        	
				credOPCUser = new UsernamePasswordCredentials(opcProperties.getProperty(OPCProperties.OPC_USERNAME), opcProperties.getProperty(OPCProperties.OPC_PASSWORD));
				
	    		response = ApacheHttpClientGet.httpClientGET(sUri, aHeaders, null, credOPCUser);
		        	
	        	System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	    		
	            break;
	        case OPCProperties.GOAL_JCS_INSTANCE_DELETE:
	        	System.out.println("JCS delete instance----------------------------------------");
	
	        	sUri = "https://" + opcProperties.getProperty(OPCProperties.OPC_BASE_URL) 
	        			+ opcProperties.getProperty(OPCProperties.JCS_REST_URL)  
	        			+ opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN) 
	        			+ "/" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_1);
	        	
	        	System.out.println(sUri);
	        	
	        	credOPCUser = new UsernamePasswordCredentials(opcProperties.getProperty(OPCProperties.OPC_USERNAME), opcProperties.getProperty(OPCProperties.OPC_PASSWORD));

	        	aHeaders = new BasicNameValuePair[3];
	        	
	        	aHeaders[0] = new BasicNameValuePair("accept", opcProperties.getProperty(OPCProperties.CONTENT_TYPE_JSON));
	        	aHeaders[1] = new BasicNameValuePair("X-ID-TENANT-NAME", opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	aHeaders[2] = new BasicNameValuePair("Content-Type", OPCProperties.CONTENT_TYPE_VND_SERVICE_JSON);
	        	
	    		StringEntity seBody = null;
	    		try {
	    			seBody = new StringEntity("{\"dbaName\":\""
	    					+ opcProperties.getProperty(OPCProperties.DBCS_DBA_NAME)
	    					+ "\",\"dbaPassword\":\""
	    					+ opcProperties.getProperty(OPCProperties.DBCS_DBA_PASSWORD)
	    					+ "\"}");
	    		} catch (UnsupportedEncodingException e) {
	    			e.printStackTrace();
	    			throw new RuntimeException("Failed to construct body: " + e.getMessage());
	    		}
	    				
	    		response = ApacheHttpClientPut.httpClientPUT(sUri, aHeaders, seBody, credOPCUser, false);
	    		
	    		System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	            break;
	        case OPCProperties.GOAL_DBCS_INSTANCE_DELETE:
	        	System.out.println("DBCS delete instance----------------------------------------");
	
	        	sUri = "https://" + opcProperties.getProperty(OPCProperties.OPC_BASE_URL) 
	        			+ opcProperties.getProperty(OPCProperties.DBCS_REST_URL)  
	        			+ opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN) 
	        			+ "/" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_1);
	        	
	        	System.out.println(sUri);
	        	
	        	credOPCUser = new UsernamePasswordCredentials(opcProperties.getProperty(OPCProperties.OPC_USERNAME), opcProperties.getProperty(OPCProperties.OPC_PASSWORD));

	        	aHeaders = new BasicNameValuePair[2];
	        	
	        	aHeaders[0] = new BasicNameValuePair("accept", opcProperties.getProperty(OPCProperties.CONTENT_TYPE_JSON));
	        	aHeaders[1] = new BasicNameValuePair("X-ID-TENANT-NAME", opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	
	    		response = ApacheHttpClientDelete.httpClientDELETE(sUri, aHeaders, null, credOPCUser);
	    		
	    		System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	            break;
	        case OPCProperties.GOAL_DBCS_INSTANCE_CREATE:
	        	System.out.println("DBCS create instance----------------------------------------");
	
	    		sbTemp = new StringBuffer();
	    		sbTemp.append("{");
	    		sbTemp.append("  \"serviceName\": \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_1) + "\",");
	    		sbTemp.append("  \"version\": \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_VERSION_1) + "\",");
	    		sbTemp.append("  \"level\": \"PAAS\",");
	    		sbTemp.append("  \"edition\": \"EE\",");
	    		sbTemp.append("  \"subscriptionType\": \"MONTHLY\",");
	    		sbTemp.append("  \"description\": \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_DESC_1) + "\",");
	    		sbTemp.append("  \"shape\": \"oc3\",");
	    		sbTemp.append("  \"vmPublicKeyText\": \"" + opcProperties.getProperty(OPCProperties.SSH_PUBLIC_KEY) + "\",");
	    		sbTemp.append("  \"parameters\": [");
	    		sbTemp.append("    {");
	    		sbTemp.append("      \"type\": \"db\",");
	    		sbTemp.append("      \"usableStorage\": \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_USABLE_STORAGE_1) + "\",");
	    		sbTemp.append("      \"adminPassword\": \"" + opcProperties.getProperty(OPCProperties.DBCS_DBA_PASSWORD) + "\",");
	    		sbTemp.append("      \"sid\": \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_SID_1) + "\",");
	    		sbTemp.append("      \"pdb\": \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_PDB1_1) + "\",");
	    		sbTemp.append("      \"failoverDatabase\": \"no\",");
	    		sbTemp.append("      \"backupDestination\": \"BOTH\",");
	    		sbTemp.append("    \"cloudStorageContainer\" : \"Storage-" + opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN) + "/" + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER) + "\",");
	    		sbTemp.append("      \"cloudStorageUser\": \"" + opcProperties.getProperty(OPCProperties.OPC_USERNAME) + "\",");
	    		sbTemp.append("      \"cloudStoragePwd\": \"" + opcProperties.getProperty(OPCProperties.OPC_PASSWORD) + "\"");
	    		sbTemp.append("    }");
	    		sbTemp.append("  ]");
	    		sbTemp.append("}");
	
	    		seBody = null;
	    		try {
	    			seBody = new StringEntity(sbTemp.toString());
	    		} catch (UnsupportedEncodingException e) {
	    			throw new RuntimeException("Failed to construct body: " + e.getMessage());
	    		}
	    		
	    		response = ApacheHttpClientPost.httpClientPOST(
	    				opcProperties.getProperty(OPCProperties.OPC_USERNAME),
	    				opcProperties.getProperty(OPCProperties.OPC_PASSWORD),
	    				opcProperties.getProperty(OPCProperties.DBCS_REST_URL),
	    				opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN),
	    				opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_1),
	    				opcProperties.getProperty(OPCProperties.DBCS_BASE_URL),
	    				OPCProperties.CONTENT_TYPE_JSON,
	    				seBody);
	    		
	    		System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	            break;
	        case OPCProperties.GOAL_JCS_INSTANCE_CREATE:
	        	System.out.println("JCS create instance----------------------------------------");
	
	    		sbTemp = new StringBuffer();
	    		sbTemp.append("{");
	    		sbTemp.append("    \"serviceName\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_1) + "\",");
	    		sbTemp.append("	   \"level\" : \"PAAS\",");
	    		sbTemp.append("    \"subscriptionType\" : \"HOURLY\",");
	    		sbTemp.append("    \"description\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_DESC_1) + "\",");
	    		sbTemp.append("    \"provisionOTD\" : " + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_OTD_1) + " ,");
	    		sbTemp.append("    \"cloudStorageContainer\" : \"Storage-" + opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN) + "/" + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER) + "\",");
	    		sbTemp.append("    \"cloudStorageUser\" : \"" + opcProperties.getProperty(OPCProperties.OPC_USERNAME) + "\",");
	    		sbTemp.append("    \"cloudStoragePassword\" : \"" + opcProperties.getProperty(OPCProperties.OPC_PASSWORD) + "\",");
	    		sbTemp.append("    \"parameters\" : [");
	    		sbTemp.append("    {");
	    		sbTemp.append("        \"type\" : \"weblogic\",");
	    		sbTemp.append("        \"version\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_VERSION_1) + "\",");
	    		sbTemp.append("        \"edition\" : \"EE\",");
	    		sbTemp.append("        \"domainMode\" : \"PRODUCTION\",");
	    		sbTemp.append("        \"managedServerCount\" : \"1\",");
	    		sbTemp.append("        \"adminPort\" : \"7001\",");
	    		sbTemp.append("        \"deploymentChannelPort\" : \"9001\",");
	    		sbTemp.append("        \"securedAdminPort\" : \"7002\",");
	    		sbTemp.append("        \"contentPort\" : \"8001\",");
	    		sbTemp.append("        \"securedContentPort\" : \"8002\",");
	    		sbTemp.append("        \"domainName\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_1) + "_domain\",");
	    		sbTemp.append("        \"clusterName\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_1) + "_cluster\",");
	    		sbTemp.append("        \"adminUserName\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_ADMIN_USER_1) + "\",");
	    		sbTemp.append("        \"adminPassword\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_ADMIN_PASSWORD_1) + "\",");
	    		sbTemp.append("        \"nodeManagerPort\" : \"6555\",");
	    		sbTemp.append("        \"nodeManagerUserName\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_ADMIN_USER_1) + "\",");
	    		sbTemp.append("        \"nodeManagerPassword\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_ADMIN_PASSWORD_1) + "\",");
	    		sbTemp.append("        \"dbServiceName\" : \"" + opcProperties.getProperty(OPCProperties.DBCS_INSTANCE_1) + "\",");
	    		sbTemp.append("        \"dbaName\" : \"" + opcProperties.getProperty(OPCProperties.DBCS_DBA_NAME) + "\",");
	    		sbTemp.append("        \"dbaPassword\" : \"" + opcProperties.getProperty(OPCProperties.DBCS_DBA_PASSWORD) + "\",");
	    		sbTemp.append("        \"shape\" : \"oc3\",");
	    		sbTemp.append("        \"domainVolumeSize\" : \"10G\",");
	    		sbTemp.append("        \"backupVolumeSize\" : \"50G\",");
	    		sbTemp.append("        \"VMsPublicKey\" : \"" + opcProperties.getProperty(OPCProperties.SSH_PUBLIC_KEY) + "\"");
	    		sbTemp.append("    }");
	    		
	    		if (opcProperties.getProperty(OPCProperties.JCS_INSTANCE_OTD_1).equals("true")) {
	    			sbTemp.append(",    {");
    				sbTemp.append("        \"type\" : \"otd\",");
    				sbTemp.append("        \"adminUserName\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_ADMIN_USER_1) + "\",");
    		        sbTemp.append("        \"adminPassword\" : \"" + opcProperties.getProperty(OPCProperties.JCS_INSTANCE_ADMIN_PASSWORD_1) + "\",");
    		        sbTemp.append("        \"listenerPortsEnabled\" : true,");
    		        sbTemp.append("        \"listenerType\" : \"http\",");
    		        sbTemp.append("        \"loadBalancingPolicy\" : \"least_connection_count\",");
    		        sbTemp.append("        \"privilegedListenerPort\" : \"80\",");
    		        sbTemp.append("        \"privilegedSecuredListenerPort\" : \"443\",");
    		        sbTemp.append("        \"adminPort\" : \"8989\",");
    		        sbTemp.append("        \"shape\" : \"oc3\",");
    		        sbTemp.append("        \"VMsPublicKey\" : \"" + opcProperties.getProperty(OPCProperties.SSH_PUBLIC_KEY) + "\"");
    		        sbTemp.append("    }");
	    		}
	    		
	    		sbTemp.append("    ]");
	    		sbTemp.append("}");
	    		
	    		try {
	    			seBody = new StringEntity(sbTemp.toString());
	    		} catch (UnsupportedEncodingException e) {
	    			throw new RuntimeException("Failed to construct body: " + e.getMessage());
	    		}
	    		
	    		response = ApacheHttpClientPost.httpClientPOST(
	    				opcProperties.getProperty(OPCProperties.OPC_USERNAME),
	    				opcProperties.getProperty(OPCProperties.OPC_PASSWORD),
	    				opcProperties.getProperty(OPCProperties.JCS_REST_URL),
	    				opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN),
	    				opcProperties.getProperty(OPCProperties.JCS_INSTANCE_1),
	    				opcProperties.getProperty(OPCProperties.OPC_BASE_URL),
	    				OPCProperties.CONTENT_TYPE_VND_SERVICE_JSON,
	    				seBody);
	    		
	    		System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	            break;
	        case OPCProperties.GOAL_JCS_GET_SPECIFIC_JOB_DETAILS:
	        	System.out.println("JCS get specific instance creation details----------------------------------------");
	
	        	aHeaders = new BasicNameValuePair[2];
	        	
	        	aHeaders[0] = new BasicNameValuePair("accept", OPCProperties.CONTENT_TYPE_JSON);
	        	aHeaders[1] = new BasicNameValuePair("X-ID-TENANT-NAME", opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	
				sUri = "http://" + opcProperties.getProperty(OPCProperties.OPC_BASE_URL) 
						+ opcProperties.getProperty(OPCProperties.JCS_REST_URL)
						+ opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN)
						+ "/" + "status/create/job/" + sJobId;
	        	
				credOPCUser = new UsernamePasswordCredentials(opcProperties.getProperty(OPCProperties.OPC_USERNAME), opcProperties.getProperty(OPCProperties.OPC_PASSWORD));
				
	    		response = ApacheHttpClientGet.httpClientGET(sUri, aHeaders, null, credOPCUser);
	
	    		System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	            break;
	        case OPCProperties.GOAL_STORAGE_CREATE:
	        	System.out.println("Create storage----------------------------------------");
	        	
	        	storageProperties = getStorageAuthToken(
	    				opcProperties.getProperty(OPCProperties.OPC_USERNAME),
	    				opcProperties.getProperty(OPCProperties.OPC_PASSWORD),
	    				opcProperties.getProperty(OPCProperties.OPC_STORAGE_GENERIC_URL),
	    				opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));

	        	sUri = storageProperties.getProperty(OPCProperties.HEADER_X_STORAGE_URL) + "/" + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER);
	        	
	        	aHeaders = new BasicNameValuePair[1];
	        	
	        	aHeaders[0] = new BasicNameValuePair("X-Auth-Token", storageProperties.getProperty(OPCProperties.HEADER_X_AUTH_TOKEN));
	        	
	        	response = ApacheHttpClientPut.httpClientPUT(sUri, aHeaders, null, null, true);
	        	
	        	System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	        	break;
	        case OPCProperties.GOAL_STORAGE_LIST:
	        	System.out.println("List storage endpoints----------------------------------------");
	        	
	        	storageProperties = getStorageAuthToken(
	    				opcProperties.getProperty(OPCProperties.OPC_USERNAME),
	    				opcProperties.getProperty(OPCProperties.OPC_PASSWORD),
	    				opcProperties.getProperty(OPCProperties.OPC_STORAGE_GENERIC_URL),
	    				opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	
	        	sUri = storageProperties.getProperty(OPCProperties.HEADER_X_STORAGE_URL);
	        	
	        	aHeaders = new BasicNameValuePair[1];
	        	
	        	aHeaders[0] = new BasicNameValuePair("X-Auth-Token", storageProperties.getProperty(OPCProperties.HEADER_X_AUTH_TOKEN));

				
	    		response = ApacheHttpClientGet.httpClientGET(sUri, aHeaders, null, null);
		        
	        	System.out.println("Output from Server .... \n");
	    		System.out.println(response);
	        	break;
	        	
	        case OPCProperties.GOAL_STORAGE_DELETE:
	        	System.out.println("Delete storage endpoints----------------------------------------");
	        	
	        	storageProperties = getStorageAuthToken(
	    				opcProperties.getProperty(OPCProperties.OPC_USERNAME),
	    				opcProperties.getProperty(OPCProperties.OPC_PASSWORD),
	    				opcProperties.getProperty(OPCProperties.OPC_STORAGE_GENERIC_URL),
	    				opcProperties.getProperty(OPCProperties.OPC_IDENTITY_DOMAIN));
	        	
	        	sUri = storageProperties.getProperty(OPCProperties.HEADER_X_STORAGE_URL);
	        	
	        	aHeaders = new BasicNameValuePair[1];
	        	
	        	aHeaders[0] = new BasicNameValuePair("X-Auth-Token", storageProperties.getProperty(OPCProperties.HEADER_X_AUTH_TOKEN));

	        	//list objects in container into file
	        	response = ApacheHttpClientGet.httpClientGET(sUri + "/" + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER), aHeaders, null, null);
	        	
	        	boolean needDelete = true;
	        	
	        	if(response.indexOf("Not Found") > -1) {
	        		System.out.println("Not found container: " + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER));
	        		needDelete = false;
	        	} else if (response.indexOf("Executon failed") > -1) {
	        		System.out.println("Execution failed for container: " + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER));
	        		needDelete = false;
	        	} else if (response.indexOf("No Content") == -1) {
	        		
	        		System.out.println("Objects query: " + (response.length() > 1000 ? response.substring(0, 1000) + "...and more" : response));
	        		
	        		response = response.replace("\n", "\n" + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER) + "/");
					response = opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER) + "/" + response; 
					response = response.substring(0, response.lastIndexOf(opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER)));
					
					System.out.println("Prepared for bulk-delete: " + (response.length() > 1000 ? response.substring(0, 1000) + "...and more" : response));
	        	
		        	aHeaders = new BasicNameValuePair[2];
		        	
		        	aHeaders[0] = new BasicNameValuePair("X-Auth-Token", storageProperties.getProperty(OPCProperties.HEADER_X_AUTH_TOKEN));
		        	aHeaders[1] = new BasicNameValuePair("Content-Type", storageProperties.getProperty(OPCProperties.CONTENT_TYPE_TEXT));
		        	
		        	seBody = null;
		        	try {
						seBody = new StringEntity(response);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	//delete objects in the container
		        	response = ApacheHttpClientDelete.httpClientDELETE(sUri + "/?bulk-delete", aHeaders, seBody, null);
			        
		        	System.out.println("Response to object delete .... \n");
		    		System.out.println(response);
	        	}
	        	
	        	//delete container
	        	if (needDelete) {
		        	aHeaders = new BasicNameValuePair[1];
		        	
		        	aHeaders[0] = new BasicNameValuePair("X-Auth-Token", storageProperties.getProperty(OPCProperties.HEADER_X_AUTH_TOKEN));
	
		        	response = ApacheHttpClientDelete.httpClientDELETE(sUri + "/" + opcProperties.getProperty(OPCProperties.OPC_STORAGE_CONTAINER), aHeaders, null, null);
		        	
		        	System.out.println("Output from Server .... \n");
		    		System.out.println(response);
	        	}
	        	break;

	        default: 
	        	System.out.println("Wrong goal specified: " + sGoal);
	            break;
			}
		}
		
	}
	
	public static Properties getStorageAuthToken(String sUsername, String sPassword,
			String sStorageUrl, String sIdentityDomain) {

		Properties storageProp = new Properties();

		try {

			CloseableHttpClient httpclient = HttpClients.custom().build();
			HttpGet httpget = new HttpGet("https://" + sIdentityDomain + "." + sStorageUrl + "/auth/v1.0");
			httpget.addHeader("X-Storage-User", "Storage-" + sIdentityDomain + ":" + sUsername);
			httpget.addHeader("X-Storage-Pass", sPassword);

			System.out.println("Executing request " + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);

			System.out.println("Response: " + response.getStatusLine());

			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("FAILED check the error : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			Header[] headers = response.getAllHeaders();

			for (Header header: headers) {
				
				if (header.getName().contains(OPCProperties.HEADER_X_AUTH_TOKEN)) {
					System.out.println("auth token:" + header.getValue());
					storageProp.setProperty(OPCProperties.HEADER_X_AUTH_TOKEN, header.getValue());
				} else if (header.getName().contains(OPCProperties.HEADER_X_STORAGE_URL)) {
					System.out.println("storage url:" + header.getValue());
					storageProp.setProperty(OPCProperties.HEADER_X_STORAGE_URL, header.getValue());
				}
			}
			
			response.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return storageProp;
	}


}