package com.nessynet.yaacs.service.mal;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.nessynet.yaacs.configuration.MalConfigurationProperties;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MalRestClient {

    private MalConfigurationProperties malConfigurationProperties;

    private RestTemplate restTemplate;

    public MalRestClient(@Autowired RestTemplate restTemplate, @Autowired MalConfigurationProperties malConfigurationProperties){
        this.restTemplate = restTemplate;
        this.malConfigurationProperties=malConfigurationProperties;
    }

    private HttpHeaders preparedHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();

        String plainCreds = malConfigurationProperties.getUsername()+":"+malConfigurationProperties.getPassword();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        headers.add("Authorization", "Basic " + base64Creds);
        return headers;
    }

    public String invokeAnimeSearch(String animeTitle){
        HttpEntity<String> httpEntity = new HttpEntity<>(preparedHttpHeaders());
        ResponseEntity<String> responseEntity= restTemplate.exchange(malConfigurationProperties.getApiUrl()+animeTitle, HttpMethod.GET, httpEntity, String.class);
        return responseEntity.getBody();
    }
}
