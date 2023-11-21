package com.example.wordsclient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.example.wordsclient.service.WordsService;

@Configuration
public class TestConfig {

    @Bean
    public WordsService wordsService() {
        return new WordsService( new RestTemplate());
    }
}