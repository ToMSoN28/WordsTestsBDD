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
public class WordsService {

    @Value("${words.api.url}")
    private String apiUrl;

    @Value("${search.endpoint}")
    private String search_endpoint;

    private final RestTemplate restTemplate;

    @Autowired
    public WordsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addWord(Word word, String password){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", password);

        HttpEntity<Word> requestEntity = new HttpEntity<>(word, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
    }


    public WordDTO[] readWords() {
        try {
            ResponseEntity<WordDTO[]> responseEntity = restTemplate.getForEntity(apiUrl, WordDTO[].class);
            return responseEntity.getBody();
        }catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
        return null;
    }

    public void deleteWord(String wordId, String password) { //id to string bo jeśli uzjemy komendy np. curl to wartość przekazywana jest jako string parsowany na int
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", password);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl + "/" + wordId,
                    HttpMethod.DELETE,
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
    }

    public void updateWord(String wordId,Word updatedWord,String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("admin", password);

        HttpEntity<Word> requestEntity = new HttpEntity<>(updatedWord, headers);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    apiUrl + "/" + wordId,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }
    }

    public Word getWordById(String wordId) { //id to string bo jeśli uzyjemy komendy np. curl to wartość przekazywana jest jako string parsowany na int
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            ResponseEntity<Word> responseEntity = restTemplate.exchange(
                    apiUrl + "/" + wordId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Word.class
            );
            return responseEntity.getBody();

        } catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);
        }

        return null;
    }

    public Word[] searchWords(String fragment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            ResponseEntity<Word[]> responseEntity = restTemplate.exchange(
                    apiUrl + "/" + search_endpoint + "/" + fragment,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    Word[].class
            );
        }catch (HttpClientErrorException e) {
            handleHttpClientErrorException(e);

        }

        return null;
    }

    private void handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.err.println("HTTP error: " + e.getStatusCode()+ " Resource not found.");
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                System.err.println("HTTP error: " + e.getStatusCode() + " Provided data is invalid.");
            } else if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                System.err.println("HTTP error: " + e.getStatusCode() + " Provided password is invalid.");
            } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                System.err.println("HTTP error: " + e.getStatusCode() + " Server error, please try again later.");
            } else {
                System.err.println("HTTP error: " + e.getStatusCode());
        }
    }
}

