package com.nessynet.yaacs.bean;

import com.nessynet.yaacs.configuration.MalConfigurationProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(MockitoJUnitRunner.class)
public class MalRestClientTest {

    @Mock
    private RestTemplate restTemplate;
    private MalConfigurationProperties malConfigurationProperties;

    @Before
    public void setUp() throws Exception {
        String body = new String(Files.readAllBytes(
                Paths.get(getClass().getClassLoader()
                        .getResource("mal/mal-anime-search-response.xml")
                        .toURI())));

        ResponseEntity responseEntity = new ResponseEntity(body, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                Matchers.startsWith("https://myanimelist.net/api/anime/search.xml?q="),
                Matchers.same(HttpMethod.GET),
                Matchers.any(HttpEntity.class),
                Matchers.same(String.class)))
                .thenReturn(responseEntity);

        malConfigurationProperties = new MalConfigurationProperties();
        malConfigurationProperties.setApiUrl("https://myanimelist.net/api/anime/search.xml?q=");
        malConfigurationProperties.setUsername("yaacs-bot");
        malConfigurationProperties.setPassword("****************");
    }

    @Test
    public void testInvokeAnimeSearch(){
        MalRestClient malRestClient = new MalRestClient(restTemplate, malConfigurationProperties);
        String response = malRestClient.invokeAnimeSearch("bleach");
        assertThat(response).isNotNull();
        assertThat(response).isNotEmpty();
    }
}
