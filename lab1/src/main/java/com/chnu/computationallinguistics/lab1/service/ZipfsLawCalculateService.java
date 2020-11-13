package com.chnu.computationallinguistics.lab1.service;

import com.chnu.computationallinguistics.lab1.model.ZipfsLaw;
import com.chnu.computationallinguistics.lab1.model.WordInVocabulary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZipfsLawCalculateService {
    private final ParseService parseService;

    public ZipfsLawCalculateService(ParseService parseService) {
        this.parseService = parseService;
    }

    Map<WordInVocabulary, ZipfsLaw> getVocabularyWithNumerationWord(String textContent) {
        Map<String, ZipfsLaw> map = calculateRank(textContent);

        Map<WordInVocabulary, ZipfsLaw> result = new LinkedHashMap<>();
        int counter = 0;
        for (Map.Entry<String, ZipfsLaw> entry : map.entrySet()) {
            result.put(new WordInVocabulary(++counter, entry.getKey()), entry.getValue());
        }

        return result;
    }

    private Map<String, ZipfsLaw> calculateRank(String textContent) {
        Map<String, Integer> map = calculateFrequency(textContent);
        Map<String, Integer> sortedMap = getSortedMapByFrequency(map);

        int lastBiggestFrequency = 0;
        int lastRank = 1;

        Map<String, ZipfsLaw> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if (entry.getValue() > lastBiggestFrequency) {
                lastBiggestFrequency = entry.getValue();
                result.put(entry.getKey(), new ZipfsLaw(entry.getValue(), lastRank));
            } else if (entry.getValue() == lastBiggestFrequency) {
                result.put(entry.getKey(), new ZipfsLaw(entry.getValue(), lastRank));
            } else {
                lastBiggestFrequency = entry.getValue();
                lastRank++;
                result.put(entry.getKey(), new ZipfsLaw(entry.getValue(), lastRank));
            }
        }

        return result;
    }

    private Map<String, Integer> getSortedMapByFrequency(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private Map<String, Integer> calculateFrequency(String textContent) {
        List<String> strings = parseService.parseText(textContent);
        Map<String, Integer> map = new HashMap<>();

        for (String string : strings) {
            if (!map.containsKey(string)) {
                map.put(string, 1);
            } else {
                map.put(string, map.get(string) + 1);
            }
        }
        return map;
    }
}