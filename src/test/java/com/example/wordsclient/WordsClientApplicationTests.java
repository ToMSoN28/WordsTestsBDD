package com.example.wordsclient;

import com.example.wordsclient.model.Word;
import com.example.wordsclient.model.WordDTO;
import com.example.wordsclient.service.AppConfig;
import com.example.wordsclient.service.WordsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {AppConfig.class, TestConfig.class})
@Import({AppConfig.class, TestConfig.class})
public class WordsClientApplicationTests {
    @Autowired
    private WordsService wordsService; // obiekt do komunikacji z api

    @Test
    public void testReadWords() {


        WordDTO[] words1 = wordsService.readWords();
        System.out.println("przeczytaj wszystkie słowa");
        for (WordDTO word : words1) {
            System.out.println("ID: " + word.getId());
            System.out.println("Słowo: " + word.getName());
        }
        System.out.println("przeczytaj słowo po id 55");
        Word word = wordsService.getWordById("55");
        if (word != null) {
            System.out.println("ID: " + word.getId());
            System.out.println("Słowo: " + word.getName());
            System.out.println("Definicja: " + word.getDefinition());
            System.out.println("Przykład: " + word.getExample());
        }


        System.out.println("przeczytaj słowo po id -55");
        word = wordsService.getWordById("-55");
        System.out.println("przeczytaj słowo po stringu");
        word = wordsService.getWordById("a234");

        System.out.println("wyszukaj słowo po fragmencie ");
        Word[] foundWords = wordsService.searchWords("ewc");
        if (foundWords != null) {
            for (Word foundWord : foundWords) {
                System.out.println("ID: " + foundWord.getId());
                System.out.println("Słowo: " + foundWord.getName());
                System.out.println("Definicja: " + foundWord.getDefinition());
                System.out.println("Przykład: " + foundWord.getExample());
            }
        }
        Word test = new Word("test1", "nie", "wiem");
        //System.out.println("dobrze dodane słowo");
        //wordsService.addWord(test,"password");
        System.out.println("złe hasło");
        wordsService.addWord(new Word("test2", "co", "tu"),"password2");
        System.out.println("źle dodane słowo");
        wordsService.addWord(new Word("test2", "wpisywać",null ),"password");
       // System.out.println("update prawidłowy");
        test.setId(152);
        test.setName("test1zmiana");
        //wordsService.updateWord(test.id.toString(),test,"password");
        wordsService.updateWord(test.id.toString(),test,"password");
        System.out.println("update złe słowo");
        test.setName(null);
        wordsService.updateWord(test.id.toString(),test,"password");
        System.out.println("update pusty");
        wordsService.updateWord(test.id.toString(),null,"password");
        System.out.println("brak slowa");
        wordsService.updateWord("151009",test,"password");
        System.out.println("nie id");
        wordsService.updateWord("abc234",test,"password");
        System.out.println("ujemne id");
        wordsService.updateWord("-234",test,"password");
        System.out.println("zle haslo");
        wordsService.updateWord("152",test,"password1");
        //System.out.println("usuwanie poprawne");
        //wordsService.deleteWord("152","password");
        System.out.println("usuwanie brak slowa");
        wordsService.deleteWord("151009","password");
        System.out.println("nie id");
        wordsService.updateWord("abc234",test,"password");
        System.out.println("ujemne id");
        wordsService.updateWord("-234",test,"password");
        System.out.println("zle haslo");
        wordsService.updateWord("152",test,"password1");

    }


}
