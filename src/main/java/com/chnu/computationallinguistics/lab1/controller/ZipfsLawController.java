package com.chnu.computationallinguistics.lab1.controller;

import com.chnu.computationallinguistics.lab1.model.ZipfsLaw;
import com.chnu.computationallinguistics.lab1.service.ZipfsLawService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/vocabulary")
public class ZipfsLawController {
    private final ZipfsLawService service;

    public ZipfsLawController(ZipfsLawService service) {
        this.service = service;
    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @PostMapping
    public String setPathToTxtFile(Model model, @RequestParam String pathToTxtFile) {
        model.addAttribute("strings", service.getContentFromTxtFile(pathToTxtFile));
        return "index";
    }

    @GetMapping("/statistic")
    @ResponseBody
    public Set<ZipfsLaw> getStatistics() {
        return service.getStatistics();
    }
}