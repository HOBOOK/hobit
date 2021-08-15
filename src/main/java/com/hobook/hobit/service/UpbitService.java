package com.hobook.hobit.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
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

    private String getToken(String accessKey, String secretKey) throws Exception{
        String queryString = "query string 생성";

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("utf8"));

        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        log.info(accessKey + " : " + secretKey);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                //.withClaim("query_hash", queryHash)
                //.withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);

        String authenticationToken = "Bearer " + jwtToken;
        return authenticationToken;
    }

    public String getAccount(String accessKey, String secretKey) throws Exception{
        accessKey = !StringUtils.hasText(accessKey) ? defaultAccessKey : accessKey;
        secretKey = !StringUtils.hasText(secretKey) ? defaultSecretKey : secretKey;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(serverUrl + "/v1/accounts");
        request.setHeader("Content-Type", "application/json");
        request.addHeader("Authorization", getToken(accessKey, secretKey));

        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();

        String responseMessage = EntityUtils.toString(entity, "UTF-8");
        log.info(responseMessage);
        return  responseMessage;
    }
}
