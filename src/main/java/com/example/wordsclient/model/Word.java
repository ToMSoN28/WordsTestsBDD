package com.example.wordsclient.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class Word {

    public Integer id;

    private String name;

    private String definition;

    private String example;

    public Word() {
    }

    public Word(String name, String definition, String example) {
        this.name = name;
        this.definition = definition;
        this.example = example;
    }
}
