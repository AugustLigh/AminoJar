package org.august.AminoApi.services;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.august.AminoApi.dto.intermediate.ProxySettings;

import java.util.Arrays;

public class ServiceApi {
    private final String BASE_URL = "https://service.narvii.com/api/v1";
    protected String getHost(){
        return BASE_URL+"/g";
    }

    protected String getHost(int comId){

        if (comId != 0) {
            return BASE_URL+"/x"+comId;
        } else {
            return this.getHost();
        }
    }

    protected CloseableHttpClient createConnection(String deviceId) {
        final BasicHeader userAgent = new BasicHeader(HttpHeaders.USER_AGENT, "Apple iPhone13 iOS v16.1.2 Main/3.13.1");
//        final BasicHeader typeHeaders = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        final BasicHeader deviceHeader = new BasicHeader("NDCDEVICEID",deviceId);


        return HttpClients.custom()
                .setDefaultHeaders(Arrays.asList(userAgent, deviceHeader))
                .build();
    }

    protected CloseableHttpClient createConnection(String deviceId, ProxySettings proxy) {
        final BasicHeader userAgent = new BasicHeader(HttpHeaders.USER_AGENT, "Apple iPhone13 iOS v16.1.2 Main/3.13.1");
//        final BasicHeader typeHeaders = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        final BasicHeader deviceHeader = new BasicHeader("NDCDEVICEID",deviceId);

        final String proxyUsername = proxy.getUsername();
        final String proxyPassword = proxy.getPassword();
        final String proxyHost = proxy.getHost();
        final int proxyPort = proxy.getPort();

        HttpHost newProxy = new HttpHost(proxyHost, proxyPort);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(
                new AuthScope(proxyHost, proxyPort),
                new UsernamePasswordCredentials(proxyUsername, proxyPassword)
        );
        return HttpClients.custom()
                .setDefaultHeaders(Arrays.asList(userAgent, deviceHeader))
                .setRoutePlanner(new DefaultProxyRoutePlanner(newProxy))
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
    }
}
