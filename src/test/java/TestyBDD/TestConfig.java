package TestyBDD;

import com.example.wordsclient.service.ResponseEntityService;
import com.example.wordsclient.service.WordsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfig {

    @Bean
    public WordsService wordsService() {
        return new WordsService( new RestTemplate());
    }

    @Bean
    public ResponseEntityService() { return new ResponseEntityService(new RestTemplate()); }
}