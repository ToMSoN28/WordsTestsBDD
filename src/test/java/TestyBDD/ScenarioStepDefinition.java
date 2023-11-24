package TestyBDD;

import com.example.wordsclient.service.ResponseEntityService;
import com.example.wordsclient.service.WordsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class ScenarioStepDefinition {

    @Autowired
    private WordsService wordsService; // obiekt do komunikacji z api

    private ResponseEntityService service;

    private String wordId = "id";
    private String word = "word";
    private String definition = "definition";
    private String example = "example";
    private String part = "part";
    private String password = "password";
    private String actualWordJson = "actualWordJson";
    private int responseCode = 123;
    private String responseJson = "responseJson";




    @When("^Request GET on endpoint /words/$")
    public void requestOnEndpointWords() {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https:127.0.0.1:8080/words/");
        try {
            // Wykonujemy zapytanie
            HttpResponse response = httpClient.execute(httpGet);

            // Pobieramy status odpowiedzi HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);
            responseCode = statusCode;

            // Pobieramy dane w formacie JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("JSON Response: " + jsonResponse);
            responseJson = jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Then("Code response {int} and json with {string} empty list")
    public void codeResponseAndJsonWithEmptyList(int arg0, String arg1) {
        Assertions.assertEquals(arg0, responseCode);
        if (arg1=="no") {
            Assertions.assertNotEquals("{}", responseJson);
        } else {
            Assertions.assertEquals("{}", responseJson);
        }
    }

    @Then("Code response {int} and json with {string}")
    public void codeResponseAndJsonWith(int arg0, String arg1) {
    }

    @Given("Correct part {string}")
    public void correctPart(String arg0) {
        part = arg0;
    }

    @When("^Request GET on endpoint /words/id$")
    public void requestOnEndpointWordsId() {
        String url = "https:127.0.0.1:8080/words/"+wordId;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            // Wykonujemy zapytanie
            HttpResponse response = httpClient.execute(httpGet);

            // Pobieramy status odpowiedzi HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);
            responseCode = statusCode;

            // Pobieramy dane w formacie JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("JSON Response: " + jsonResponse);
            responseJson = jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Request GET on endpoint /words/search/fragment$")
    public void requestOnEndpointWordsSearchFragment() {
        String url = "https:127.0.0.1:8080/words/search/"+part;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            // Wykonujemy zapytanie
            HttpResponse response = httpClient.execute(httpGet);

            // Pobieramy status odpowiedzi HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);
            responseCode = statusCode;

            // Pobieramy dane w formacie JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("JSON Response: " + jsonResponse);
            responseJson = jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("Code response {int}")
    public void codeResponse(int arg0) {
        Assertions.assertEquals(arg0, responseCode);
    }

    @Given("wordID = {string}")
    public void wordid(String arg0) {
        wordId=arg0;
    }

    @Given("Word {string}")
    public void word(String arg0) {
        word = arg0;
    }

    @Given("Definition {string}")
    public void definition(String arg0) {
        definition=arg0;
    }

    @Given("Password {string}")
    public void password(String arg0) {
        password=arg0;
    }

    @When("^Request POST on endpoint /words/ with json$")
    public void requestPOSTOnEndpointWordsWithJson() {
        String json = "{\"name\":\"" + word + "\",\"definition\":\"" + definition + "\",\"example\":\"" + example + "\"}";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https:127.0.0.1:8080/words/");
        try {
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            responseCode = response.getStatusLine().getStatusCode();
            responseJson = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Then("Get word with {string} and compare with given inputs")
    public void getWordWithAndCompareWithGivenInputs(String arg0) {
        String url = "https:127.0.0.1:8080/words/"+arg0;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            // Wykonujemy zapytanie
            HttpResponse response = httpClient.execute(httpGet);

            // Pobieramy status odpowiedzi HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);
            responseCode = statusCode;

            // Pobieramy dane w formacie JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("JSON Response: " + jsonResponse);
            responseJson = jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Inicjalizacja obiektu ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Przekształcenie JSON na Map
            Map<String, Object> map = objectMapper.readValue(responseJson, Map.class);

            // Wyświetlenie wyniku
            System.out.println("Map: " + map);

            // Możesz teraz używać mapy w swoim kodzie
            Assertions.assertEquals(word, (String)map.get("name"));
            Assertions.assertEquals(definition, (String)map.get("definition"));
            Assertions.assertEquals(example, (String)map.get("example"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("Example {string}")
    public void example(String arg0) {
        example=arg0;
    }

    @When("^Request POST on endpoint /words/ with incorrect json formant$")
    public void requestPOSTOnEndpointWordsWithIncorrectJsonFormant() {
        //in json supposed to be name instead of word
        //in json supposed to be definition instead of def
        //in json supposed to be example instead of ex
        String json = "{\"word\":\"" + word + "\",\"def\":\"" + definition + "\",\"ex\":\"" + example + "\"}";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https:127.0.0.1:8080/words/");
        try {
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            responseCode = response.getStatusLine().getStatusCode();
            responseJson = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Request PUT on endpoint /words/id with json$")
    public void requestPUTOnEndpointWordsIdWithJson() {
        String json = "{\"name\":\"" + word + "\",\"definition\":\"" + definition + "\",\"example\":\"" + example + "\"}";
        HttpClient httpClient = HttpClients.createDefault();
        String url = "https:127.0.0.1:8080/words/"+wordId;
        HttpPut httpPut = new HttpPut(url);
        try {
            StringEntity stringEntity = new StringEntity(json);
            httpPut.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPut);
            responseCode = response.getStatusLine().getStatusCode();
            responseJson = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Request PUT on endpoint /words/id with incorrect json$")
    public void requestPUTOnEndpointWordsIdWithIncorrectJson() {
        //in json supposed to be name instead of word
        //in json supposed to be definition instead of def
        //in json supposed to be example instead of ex
        String json = "{\"word\":\"" + word + "\",\"def\":\"" + definition + "\",\"ex\":\"" + example + "\"}";
        HttpClient httpClient = HttpClients.createDefault();
        String url = "https:127.0.0.1:8080/words/"+wordId;
        HttpPost httpPost = new HttpPost(url);
        try {
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);
            HttpResponse response = httpClient.execute(httpPost);
            responseCode = response.getStatusLine().getStatusCode();
            responseJson = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("Get actual word")
    public void getActualWord() {
        String url = "https:127.0.0.1:8080/words/"+wordId;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            // Wykonujemy zapytanie
            HttpResponse response = httpClient.execute(httpGet);

            // Pobieramy dane w formacie JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("JSON Response: " + jsonResponse);
            actualWordJson = jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }    }

    @Then("Compare with latest version")
    public void compareWithLatestVersion() {
        String url = "https:127.0.0.1:8080/words/"+wordId;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            // Wykonujemy zapytanie
            HttpResponse response = httpClient.execute(httpGet);

            // Pobieramy status odpowiedzi HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Status Code: " + statusCode);
            responseCode = statusCode;

            // Pobieramy dane w formacie JSON
            String jsonResponse = EntityUtils.toString(response.getEntity());
            System.out.println("JSON Response: " + jsonResponse);
            responseJson = jsonResponse;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(actualWordJson, responseJson);
    }

    @When("^Request DELETE on endpoint /words/id$")
    public void requestDELETEOnEndpointWordsId() {
        String url = "https:127.0.0.1:8080/words/"+wordId;
        HttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);

        try {
            // Wykonujemy żądanie DELETE
            HttpResponse response = httpClient.execute(httpDelete);

            // Pobieramy status odpowiedzi HTTP
            int statusCode = response.getStatusLine().getStatusCode();
            responseCode = statusCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("Code response {int} and json with new id")
    public void codeResponseAndJsonWithNewId(int arg0) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(responseJson, Map.class);

            wordId = (String)map.get("id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(arg0, responseCode);

    }
}
