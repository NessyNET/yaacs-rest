package com.nessynet.yaacs.configuration;

import com.nessynet.yaacs.bean.MalHtmlScraper;
import com.nessynet.yaacs.bean.MalJooxParser;
import com.nessynet.yaacs.bean.MalRestClient;
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

    @Bean
    public MalJooxParser malJooxParser(){
        return new MalJooxParser();
    }

    @Bean
    public MalHtmlScraper malHtmlScrapper(){
        return new MalHtmlScraper();
    }
}
