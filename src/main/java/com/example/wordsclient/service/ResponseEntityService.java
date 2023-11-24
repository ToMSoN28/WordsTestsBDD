package com.example.wordsclient.service;
import com.example.wordsclient.model.Word;
import com.example.wordsclient.model.WordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class ResponseEntityService {

    @Value("${words.api.url}")
    private String apiUrl;

    @Value("${search.endpoint}")
    private String search_endpoint;

    private final RestTemplate restTemplate;

    @Autowired
    public ResponseEntityService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> addWord(Word word, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", password);

        HttpEntity<Word> requestEntity = new HttpEntity<>(word, headers);

        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
        }

        return responseEntity;
    }


    public ResponseEntity<WordDTO[]> readWords() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<WordDTO[]> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    WordDTO[].class
            );
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseHeaders(), e.getStatusCode());
        }

        return responseEntity;
    }

    public ResponseEntity<String> deleteWord(String wordId, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", password);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    apiUrl + "/" + wordId,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseBodyAsString(), e.getResponseHeaders(), e.getStatusCode());
        }

        return responseEntity;
    }

    public ResponseEntity<String> updateWord(String wordId, Word updatedWord, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", password);

        HttpEntity<Word> requestEntity = new HttpEntity<>(updatedWord, headers);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        try {
            responseEntity = restTemplate.exchange(
                    apiUrl + "/" + wordId,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getResponseHeaders(), e.getStatusCode());
        }

        return responseEntity;
    }

    public ResponseEntity<Word> getWordById(String wordId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Word> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(
                    apiUrl + "/" + wordId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Word.class
            );
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseHeaders(), e.getStatusCode());
        }

        return responseEntity;
    }
    public ResponseEntity<Word[]> searchWords(String fragment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Word[]> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(
                    apiUrl + "/" + search_endpoint + "/" + fragment,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Word[].class
            );
        } catch (HttpClientErrorException e) {
            responseEntity = new ResponseEntity<>(e.getResponseHeaders(), e.getStatusCode());
        }

        return responseEntity;
    }

}
