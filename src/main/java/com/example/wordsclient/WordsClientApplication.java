package com.example.wordsclient;

import com.example.wordsclient.service.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class WordsClientApplication {



    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
        context.close();

    }

}
