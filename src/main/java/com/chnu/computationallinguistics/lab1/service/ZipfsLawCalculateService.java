package com.chnu.computationallinguistics.lab1.service;

import com.chnu.computationallinguistics.lab1.model.WordInVocabulary;
import com.chnu.computationallinguistics.lab1.model.ZipfsLaw;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ZipfsLawCalculateService {
    private final EndOfTheWordService endOfTheWordService;

    public ZipfsLawCalculateService(EndOfTheWordService endOfTheWordService) {
        this.endOfTheWordService = endOfTheWordService;
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
        List<String> strings = parseText(textContent);
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

    private List<String> parseText(String textContent) {
        List<String> result = new ArrayList<>();

        char[] chars = textContent.toCharArray();
        String[] arrayOfStrings = convertCharArrayToStringArray(chars);

        StringBuilder word = new StringBuilder();

        Set<String> endOfTheWords = endOfTheWordService.getEndOfTheWord();

        for (int i = 0; i < arrayOfStrings.length; i++) {
            if (arrayOfStrings[i].matches("[a-zA-Zа-яА-Яії]+")) {
                word.append(arrayOfStrings[i]);
            } else if (!word.toString().isBlank()) {
                setWordToList(result, word, endOfTheWords);
            }
            if (i == arrayOfStrings.length - 1) {
                setWordToList(result, word, endOfTheWords);
            }
        }
        return result;
    }

    private void setWordToList(List<String> result, StringBuilder word, Set<String> endOfTheWords) {
        if (word.length() <= 3) {
            result.add(word.toString());
            word.setLength(0);
        } else {
            String wordWithoutEnding = excludeEndingInWord(word, endOfTheWords);
            result.add(wordWithoutEnding);
            word.setLength(0);
        }
    }

    private String excludeEndingInWord(StringBuilder word, Set<String> endOfTheWords) {
        String currentWord = word.toString();
        for (String end : endOfTheWords) {
            if (currentWord.endsWith(end)) {
                return currentWord.substring(0, currentWord.indexOf(end));
            }
        }
        return currentWord;
    }

    private String[] convertCharArrayToStringArray(char[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]).toLowerCase();
        }
        return result;
    }
}