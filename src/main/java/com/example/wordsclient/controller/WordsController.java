package com.example.wordsclient.controller;

import com.example.wordsclient.model.Word;
import com.example.wordsclient.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class WordsController {

    private final WordsService wordsService;

    @Autowired
    public WordsController(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    @GetMapping("/")
    public String getWords(Model model) {
        model.addAttribute("words", wordsService.readWords());
        return "words";
    }

    @PostMapping("/words")
    public String addWord(@ModelAttribute Word word, @RequestParam String password) {
        wordsService.addWord(word, password);
        return "redirect:/";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("word", new Word());
        return "add";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        Word word = wordsService.getWordById(id);
        System.out.println(word.id);
        model.addAttribute("word", word);
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Word word, @RequestParam String password) {
        wordsService.updateWord(word.getId().toString(), word, password);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String id, @RequestParam String password) {
        wordsService.deleteWord(id, password);
        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(@RequestParam String name, Model model) {
        model.addAttribute("words", wordsService.searchWords(name));
        return "searchResults";
    }
}

