package com.chnu.computationallinguistics.lab2.service;

import com.chnu.computationallinguistics.lab2.model.WordInVocabulary;
import com.chnu.computationallinguistics.lab2.model.ZipfsLaw;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ReverseDictionaryService {
    private final ReverseDictionaryCalculateService reverseDictionaryCalculateService;
    private Set<ZipfsLaw> statistics;

    public ReverseDictionaryService(ReverseDictionaryCalculateService reverseDictionaryCalculateService) {
        this.reverseDictionaryCalculateService = reverseDictionaryCalculateService;
    }

    public Map<WordInVocabulary, ZipfsLaw> getContentFromTxtFile(String pathToTxtFile, String sortDictionary) {
        if (pathToTxtFile.endsWith(".txt")) {
            StringBuilder content = new StringBuilder();

            File file = new File(pathToTxtFile);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
            } catch (IOException e) {
                return new LinkedHashMap<>();
            }

            Map<WordInVocabulary, ZipfsLaw> stringZipfsLaws = reverseDictionaryCalculateService.getVocabularyWithNumerationWord(content.toString(), sortDictionary);
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