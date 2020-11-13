package com.chnu.computationallinguistics.lab2.service;

import com.chnu.computationallinguistics.lab2.model.WordInVocabulary;
import com.chnu.computationallinguistics.lab2.model.ZipfsLaw;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReverseDictionaryCalculateService {
    private final ParseService parseService;
    private String sortDictionary;

    private String getSortDictionary() {
        return sortDictionary;
    }

    private void setSortDictionary(String sortDictionary) {
        this.sortDictionary = sortDictionary;
    }

    public ReverseDictionaryCalculateService(ParseService parseService) {
        this.parseService = parseService;
    }

    Map<WordInVocabulary, ZipfsLaw> getVocabularyWithNumerationWord(String textContent, String sortDictionary) {
        setSortDictionary(sortDictionary);
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
        map = getSortedMap(map);

        int lastBiggestFrequency = 0;
        int lastRank = 1;

        Map<String, ZipfsLaw> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
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

    private Map<String, Integer> getSortedMap(Map<String, Integer> map) {
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        if (getSortDictionary().equalsIgnoreCase("reverse")) {
            sortedMap = getReverseSortedKey(map);
        } else if (getSortDictionary().equalsIgnoreCase("direct")) {
            sortedMap = getDirectSortedKey(map);
        } else if (getSortDictionary().equalsIgnoreCase("frequency")) {
            sortedMap = getFrequencySortedValue(map);
        }
        return sortedMap;
    }

    private Map<String, Integer> getReverseSortedKey(Map<String, Integer> map) {
        Map<String, Integer> reverseWordMap = new LinkedHashMap<>();
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String reverseWord = sb.append(entry.getKey()).reverse().toString();
            reverseWordMap.put(reverseWord, entry.getValue());
            sb.setLength(0);
        }

        Map<String, Integer> collect = reverseWordMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        reverseWordMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : collect.entrySet()) {
            String reverseWord = sb.append(entry.getKey()).reverse().toString();
            reverseWordMap.put(reverseWord, entry.getValue());
            sb.setLength(0);
        }

        return reverseWordMap;
    }

    private Map<String, Integer> getDirectSortedKey(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private Map<String, Integer> getFrequencySortedValue(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}