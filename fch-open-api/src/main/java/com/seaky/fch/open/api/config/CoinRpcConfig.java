package com.seaky.fch.open.api.config;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CoinRpcConfig {

    @Value("fch.rpc.address")
    private String fchNodeAddr;

    @Value("fch.rpc.user")
    private String fchUser;

    @Value("fch.rpc.password")
    private String fchPassword;


    public String getFchNodeAddr() {
        return fchNodeAddr;
    }

    public void setFchNodeAddr(String fchNodeAddr) {
        this.fchNodeAddr = fchNodeAddr;
    }

    public String getFchUser() {
        return fchUser;
    }

    public void setFchUser(String fchUser) {
        this.fchUser = fchUser;
    }

    public String getFchPassword() {
        return fchPassword;
    }

    public void setFchPassword(String fchPassword) {
        this.fchPassword = fchPassword;
    }

    //@Bean(name="fchRpcClient")
    public JsonRpcHttpClient createFchClient() {
        JsonRpcHttpClient client = null;
        String cred = Base64.getEncoder().encodeToString((fchUser + ":" + fchPassword).getBytes());
        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("Authorization", "Basic " + cred);
        try {
            client = new JsonRpcHttpClient(new URL("http://" + fchNodeAddr), headers);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

}
