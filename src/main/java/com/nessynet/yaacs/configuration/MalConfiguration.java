package com.nessynet.yaacs.configuration;

import com.nessynet.yaacs.service.mal.MalRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MalConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public MalRestClient malRestClient(MalConfigurationProperties malConfigurationProperties){
        return new MalRestClient(restTemplate(), malConfigurationProperties);
    }
}
