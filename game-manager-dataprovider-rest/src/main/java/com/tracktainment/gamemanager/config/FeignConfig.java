package com.tracktainment.gamemanager.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean("http2PatchEnabler")
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
