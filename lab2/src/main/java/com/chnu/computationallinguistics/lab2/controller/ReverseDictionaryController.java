package com.chnu.computationallinguistics.lab2.controller;

import com.chnu.computationallinguistics.lab2.model.ZipfsLaw;
import com.chnu.computationallinguistics.lab2.service.ReverseDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/vocabulary")
public class ReverseDictionaryController {
    private final ReverseDictionaryService service;

    public ReverseDictionaryController(ReverseDictionaryService service) {
        this.service = service;
    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @PostMapping
    public String setPathToTxtFile(Model model, @RequestParam String pathToTxtFile, @RequestParam String sortDictionary) {
        model.addAttribute("strings", service.getContentFromTxtFile(pathToTxtFile, sortDictionary));
        return "index";
    }

    @GetMapping("/statistic")
    @ResponseBody
    public Set<ZipfsLaw> getStatistics() {
        return service.getStatistics();
    }
}