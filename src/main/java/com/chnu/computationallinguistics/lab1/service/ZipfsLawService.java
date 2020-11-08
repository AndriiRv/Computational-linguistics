package com.chnu.computationallinguistics.lab1.service;

import com.chnu.computationallinguistics.lab1.model.WordInVocabulary;
import com.chnu.computationallinguistics.lab1.model.ZipfsLaw;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class ZipfsLawService {
    private final ZipfsLawCalculateService zipfsLawCalculateService;
    private Set<ZipfsLaw> statistics;

    public ZipfsLawService(ZipfsLawCalculateService zipfsLawCalculateService) {
        this.zipfsLawCalculateService = zipfsLawCalculateService;
    }

    public Map<WordInVocabulary, ZipfsLaw> getContentFromTxtFile(String pathToTxtFile) {
        if (pathToTxtFile.endsWith(".txt")) {
            StringBuilder content = new StringBuilder();

            File file = new File(pathToTxtFile);
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
            } catch (IOException e) {
                return new LinkedHashMap<>();
            }

            Map<WordInVocabulary, ZipfsLaw> stringZipfsLaws = zipfsLawCalculateService.getVocabularyWithNumerationWord(content.toString());
            statistics = new LinkedHashSet<>(stringZipfsLaws.values());

            return stringZipfsLaws;
        } else {
            return null;
        }
    }

    public Set<ZipfsLaw> getStatistics() {
        return statistics;
    }
}