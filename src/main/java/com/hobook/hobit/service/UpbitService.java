package com.hobook.hobit.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
@Service
public class UpbitService {
    @Value(value = "${upbit.access-key}")
    private String defaultAccessKey;
    @Value(value = "${upbit.secret-key}")
    private String defaultSecretKey;
    @Value(value = "${upbit.server-url}")
    private String serverUrl;

    private String getToken(String accessKey, String secretKey, HashMap<String, String> params) throws Exception{


        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = "";
        if(params != null){
            String queryString = getQueryString(params);

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(queryString.getBytes("utf8"));
            String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));

            jwtToken = JWT.create()
                    .withClaim("access_key", accessKey)
                    .withClaim("nonce", UUID.randomUUID().toString())
                    .withClaim("query_hash", queryHash)
                    .withClaim("query_hash_alg", "SHA512")
                    .sign(algorithm);
        }else{
            jwtToken = JWT.create()
                    .withClaim("access_key", accessKey)
                    .withClaim("nonce", UUID.randomUUID().toString())
                    .sign(algorithm);
        }


        String authenticationToken = "Bearer " + jwtToken;
        return authenticationToken;
    }

    private String getQueryString(HashMap<String, String> params) {
        ArrayList<String> queryElements = new ArrayList<>();
        for(Map.Entry<String, String> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        return String.join("&", queryElements.toArray(new String[0]));
    }

    private String requestGet(String accessKey, String secretKey, String requestUrl, HashMap<String, String> params) throws Exception{
        accessKey = !StringUtils.hasText(accessKey) ? defaultAccessKey : accessKey;
        secretKey = !StringUtils.hasText(secretKey) ? defaultSecretKey : secretKey;
        HttpClient client = HttpClientBuilder.create().build();

        String endPoint = params == null ? serverUrl + "/v1/" + requestUrl : serverUrl + "/v1/" + requestUrl + "?" + getQueryString(params);
        HttpGet request = new HttpGet(endPoint);
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", getToken(accessKey, secretKey, params));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String responseMessage = EntityUtils.toString(entity, "UTF-8");
        return  responseMessage;
    }

    private String requestPost(String accessKey, String secretKey, String requestUrl, HashMap<String, String> params) throws Exception{
        accessKey = !StringUtils.hasText(accessKey) ? defaultAccessKey : accessKey;
        secretKey = !StringUtils.hasText(secretKey) ? defaultSecretKey : secretKey;
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(serverUrl + "/v1/" + requestUrl);
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", getToken(accessKey, secretKey, params));
        request.setEntity(new StringEntity(new Gson().toJson(params)));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String responseMessage = EntityUtils.toString(entity, "UTF-8");
        return  responseMessage;
    }

    public String getMarkets(String accessKey, String secretKey) throws Exception{
        HashMap<String, String> params = new HashMap<>();
        params.put("isDetail", "false");
        return requestGet(accessKey, secretKey, "market/all", params);
    }

    public String getAccount(String accessKey, String secretKey) throws Exception{
        return requestGet(accessKey, secretKey, "accounts", null);
    }

    public String getTicker(String accessKey, String secretKey, String markets) throws Exception{
        HashMap<String, String> params = new HashMap<>();
        params.put("markets", markets);
        return requestGet(accessKey, secretKey, "ticker", params);
    }

}
