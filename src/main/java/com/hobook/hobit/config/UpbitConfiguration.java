package com.hobook.hobit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpbitConfiguration {
    @Value(value = "upbit.access-key")
    private String accessKey;
    @Value(value = "upbit.secret-key")
    private String secretKey;
}
