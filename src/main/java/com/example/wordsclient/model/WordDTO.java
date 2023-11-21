package com.example.wordsclient.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
//tylko dla endpointu /words zeby zwrócić lisTę słów i id
public class WordDTO {

    public Integer id;

    private String name;

    public WordDTO() {
    }

}
