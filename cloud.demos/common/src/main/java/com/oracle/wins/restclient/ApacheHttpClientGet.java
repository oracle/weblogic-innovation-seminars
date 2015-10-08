package com.oracle.wins.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.oracle.wins.util.restclient.util.OPCProperties;

public class ApacheHttpClientGet {

	public static String httpClientGET(String sUri,
			BasicNameValuePair[] aHeaders, StringEntity seBody,
			Credentials credOPCUser) {

		StringBuffer sbOutput = new StringBuffer();

		try {

			CloseableHttpClient httpclient = null;
			if (credOPCUser != null) {
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(new AuthScope(OPCProperties
						.getInstance().getProperty(OPCProperties.OPC_BASE_URL),
						443), credOPCUser);
				System.out.println("Auth: " + credsProvider.toString());

				httpclient = HttpClients.custom()
						.setDefaultCredentialsProvider(credsProvider).build();
			} else {
				httpclient = HttpClients.custom().build();
			}

			System.out.println("URI: " + sUri);
			HttpGet httpGet = new HttpGet(sUri);

			for (BasicNameValuePair header : aHeaders) {
				httpGet.addHeader(header.getName(), header.getValue());
			}

			System.out.println("Executing request " + httpGet.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpGet);

			System.out.println("Response: " + response.getStatusLine());

			if (!(response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 204 
					|| response.getStatusLine().getStatusCode() == 202)) {
				System.out
						.println("FAILED check the error : HTTP error code : "
								+ response.getStatusLine().getStatusCode());
				System.out.println("Reason: " + response.getStatusLine().getReasonPhrase());
				if (response.getEntity() == null) {
					return "Executon failed";
				}
			}

			if (response.getEntity() != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(response.getEntity().getContent())));
				String sTemp;
	
				while ((sTemp = br.readLine()) != null) {
					sbOutput.append(sTemp).append("\n");
				}
			} else {
				sbOutput.append(response.getStatusLine()).append("\n");
			}
			
			response.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return sbOutput.toString();
	}

}