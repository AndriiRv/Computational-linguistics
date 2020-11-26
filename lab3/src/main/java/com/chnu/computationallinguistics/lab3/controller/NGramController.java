package com.chnu.computationallinguistics.lab3.controller;

import com.chnu.computationallinguistics.lab3.model.NGramModel;
import com.chnu.computationallinguistics.lab3.service.NGramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NGramController {
    private NGramService nGramService;

    public NGramController(NGramService nGramService) {
        this.nGramService = nGramService;
    }

    @GetMapping("/ngram")
    public String index() {
        return "index";
    }

    @PostMapping("/ngram")
    public String getNGram(Model model, @RequestParam String firstStr, @RequestParam String secondStr, @RequestParam int lengthOfGram) {
        double relevance = nGramService.getRelevance(new NGramModel(firstStr, secondStr, lengthOfGram));
        String relevanceTwoStr = nGramService.getRelevanceTwoStr();

        model.addAttribute("relevance", relevance);
        model.addAttribute("relevanceTwoStr", relevanceTwoStr);

        return "index";
    }
}