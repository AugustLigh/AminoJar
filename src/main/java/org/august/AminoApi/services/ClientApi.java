package org.august.AminoApi.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.august.AminoApi.dto.intermediate.ProxySettings;
import org.august.AminoApi.dto.requests.DataAuthDTO;
import org.august.AminoApi.dto.requests.SendMsgDTO;
import org.august.AminoApi.dto.response.AuthDto;
import org.august.AminoApi.dto.response.FromCodeDTO;
import org.august.AminoApi.dto.response.LinkInfo;
import org.august.AminoApi.generators.DeviceGenerator;
import org.august.AminoApi.generators.SigGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class ClientApi extends ServiceApi{
    public Gson getGson() {
        return gson;
    }

    private final Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    private final String login;

    private final String password;

    public String getDeviceId() {
        return deviceId;
    }

    private final String deviceId;

    public String getSid() {
        return sid;
    }

    private String sid;
    private String userId;

    public int getComId() {
        return comId;
    }

    public void setComId(int comId) {
        this.comId = comId;
    }

    private int comId = 0;
    private final Random rand = new Random();

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    private CloseableHttpClient httpClient;

    public ClientApi() {
        this(null);
    }

    public ClientApi(String sid) {
        this.sid = sid;
        this.login = null;
        this.password = null;
        this.deviceId = DeviceGenerator.genDeviceId();
        this.httpClient = this.createConnection(deviceId);
    }

    public ClientApi(String sid, ProxySettings proxy) {
        this.sid = sid;
        this.login = null;
        this.password = null;
        this.deviceId = DeviceGenerator.genDeviceId();
        this.httpClient = this.createConnection(deviceId, proxy);
    }

    public ClientApi(String login, String password) {
        this.login = login;
        this.password = password;
        this.deviceId = DeviceGenerator.genDeviceId();
        this.httpClient = this.createConnection(deviceId);
        this.authorization();
    }

    public ClientApi(String login, String password, String deviceId) {
        this.login = login;
        this.password = password;
        this.deviceId = deviceId;
        this.httpClient = this.createConnection(deviceId);
        this.authorization();
    }

    public ClientApi(String login, String password, ProxySettings proxy) {
        this.login = login;
        this.password = password;
        this.deviceId = DeviceGenerator.genDeviceId();
        this.httpClient = this.createConnection(this.deviceId, proxy);
        this.authorization();
    }

    public ClientApi(String login, String password, String deviceId, ProxySettings proxy) {
        this.login = login;
        this.password = password;
        this.deviceId = deviceId;
        this.httpClient = this.createConnection(deviceId, proxy);
        this.authorization();
    }

    public void setProxy(@NotNull ProxySettings proxy) {
        this.httpClient = this.createConnection(this.deviceId, proxy);
    }

    public AuthDto authorization() {
        return this.authorization(this.login, this.password);
    }

    public AuthDto authorization(String login, String password) {
        DataAuthDTO bodyData = new DataAuthDTO(login, password);
        String requestBody = gson.toJson(bodyData);
        HttpPost httpPost = new HttpPost(getHost()+"/s/auth/login/");
        httpPost.setHeader("NDC-MSG-SIG", SigGenerator.genSig(requestBody));

        httpPost.setEntity(new StringEntity(requestBody, "UTF-8"));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity, "UTF-8");
            AuthDto dtoObject = gson.fromJson(responseBody, AuthDto.class);
            this.sid = dtoObject.getSid();
            this.userId = dtoObject.getAuid();
            System.out.println(this.userId);
            return dtoObject;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends a message to a chat.
     *
     * @param chatId      The identifier of the chat to which the message is being sent.
     * @param content     The content of the message.
     * @throws RuntimeException if an error occurs while executing the HTTP request.
     */
    public void sendMessage(String chatId, String content) {
        this.sendMessage(chatId , content ,0, 0);
    }

    /**
     * Sends a message to a chat.
     *
     * @param chatId      The identifier of the chat to which the message is being sent.
     * @param content     The content of the message.
     * @param comId       The community identifier.
     * @throws RuntimeException if an error occurs while executing the HTTP request.
     */
    public void sendMessage(String chatId, String content, int comId){
        this.sendMessage(chatId, content, comId, 0);
    }

    /**
     * Sends a message to a chat.
     *
     * @param chatId      The identifier of the chat to which the message is being sent.
     * @param content     The content of the message.
     * @param comId       The community identifier.
     * @param messageType The type of message (e.g., 1, 50, 51, etc.).
     * @throws RuntimeException if an error occurs while executing the HTTP request.
     */
    public void sendMessage(String chatId, String content, int comId, int messageType){
        SendMsgDTO bodyData = new SendMsgDTO(content, messageType);
        String requestBody = gson.toJson(bodyData);

        HttpPost httpPost = new HttpPost(getHost(comId)+"/s/chat/thread/"+chatId+"/message");
        httpPost.setHeader("NDC-MSG-SIG", SigGenerator.genSig(requestBody));
        httpPost.setHeader("NDCAUTH", "sid="+this.sid);

        httpPost.setEntity(new StringEntity(requestBody, "UTF-8"));

        try {
            httpClient.execute(httpPost).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
        Get information from link
        @param url The link to object in Amino
        @return LinkInfo object
     */
    public LinkInfo getFromCode(String url){
        HttpGet HttpGet = new HttpGet(getHost()+"/s/link-resolution?q="+url);
        HttpGet.setHeader("NDCAUTH", "sid="+this.sid);

        try (CloseableHttpResponse response = httpClient.execute(HttpGet)) {
            HttpEntity responseEntity = response.getEntity();
            String responseBody = EntityUtils.toString(responseEntity, "UTF-8");
            return gson.fromJson(responseBody, FromCodeDTO.class).getLinkInfoV2().getExtensions().getLinkInfo();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Join to chat from chatId
     * @param chatId ID of the chat you want to join
     */
    public void joinChat(String chatId) {
        int randomNum = rand.nextInt((999999 - 1) + 1) + 1;

        HttpPost httpPost = new HttpPost(getHost(comId)+"/s/chat/thread/"+chatId+"/member/"+this.userId);
        httpPost.setHeader("NDCAUTH", "sid="+this.sid);
        httpPost.setHeader("User-Agent", "Apple iPhone"+randomNum+" iOS v16.1.2 Main/3.13.1");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try {
            httpClient.execute(httpPost).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Leave from chat from chatId
     * @param chatId ID of the chat you want to join
     */
    public void leaveChat(String chatId) {
        int randomNum = rand.nextInt((999999 - 1) + 1) + 1;

        HttpDelete httpDelete = new HttpDelete(getHost(comId)+"/s/chat/thread/"+chatId+"/member/"+this.userId);
        httpDelete.setHeader("NDCAUTH", "sid="+this.sid);
        httpDelete.setHeader("User-Agent", "Apple iPhone"+randomNum+" iOS v16.1.2 Main/3.13.1");

        try {
            httpClient.execute(httpDelete).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
