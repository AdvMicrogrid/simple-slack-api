package com.ullink.slack.simpleslackapi.impl;

import com.ullink.slack.simpleslackapi.WebSocketContainerProvider;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.WebSocketContainer;

public class DefaultWebSocketContainerProvider implements WebSocketContainerProvider
{

    private String proxyAddress;
    private int proxyPort;
    private String proxyUser;
    private String proxyPassword;

    DefaultWebSocketContainerProvider(String proxyAdress, int proxyPort, String proxyUser, String proxyPassword) {
        this.proxyAddress = proxyAdress;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPassword = proxyPassword;
    }

    @Override
    public WebSocketContainer getWebSocketContainer()
    {
        ClientManager clientManager = ClientManager.createClient();
        if (proxyAddress != null)
        {
            clientManager.getProperties().put(ClientProperties.PROXY_URI, "http://" + proxyAddress + ":" + proxyPort);
        }
        if (proxyUser != null)
        {
            Map<String, String> headers = new HashMap<>();
            headers.put("Proxy-Authorization", "Basic " + Base64.getEncoder().encodeToString((proxyUser + ":" + proxyPassword).getBytes(StandardCharsets.UTF_8)));
            clientManager.getProperties().put(ClientProperties.PROXY_HEADERS, headers);
        }
        return clientManager;
    }
}
