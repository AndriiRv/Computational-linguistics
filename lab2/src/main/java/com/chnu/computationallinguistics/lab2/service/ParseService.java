package com.chnu.computationallinguistics.lab2.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ParseService {
    private final EndOfTheWordService endOfTheWordService;

    public ParseService(EndOfTheWordService endOfTheWordService) {
        this.endOfTheWordService = endOfTheWordService;
    }

    List<String> parseText(String textContent) {
        List<String> rawWords = new ArrayList<>();

        char[] chars = textContent.toCharArray();
        String[] arrayOfStrings = convertCharArrayToStringArray(chars);

        Set<String> endOfTheWords = endOfTheWordService.getEndOfTheWord();

        StringBuilder word = new StringBuilder();

        for (int i = 0; i < arrayOfStrings.length; i++) {
            if (isSymbolIsLetter(arrayOfStrings, i)) {
                word.append(arrayOfStrings[i]);
            } else if (isWordNotBlank(word)) {
                setWordToList(rawWords, word, endOfTheWords);
            }
            if (isLastLetterInWord(arrayOfStrings, i)) {
                setWordToList(rawWords, word, endOfTheWords);
            }
        }

        List<String> words = additionalClearEmptyStringSymbol(rawWords);

        return words;
    }

    private List<String> additionalClearEmptyStringSymbol(List<String> list) {
        return list.stream()
                .filter(e -> !e.isBlank()).collect(Collectors.toList());
    }

    private String[] convertCharArrayToStringArray(char[] array) {
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]).toLowerCase();
        }
        return result;
    }

    private boolean isSymbolIsLetter(String[] arrayOfStrings, int iteration) {
        return arrayOfStrings[iteration].matches("[a-zA-Zа-яА-Яії]+");
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

    private boolean isWordNotBlank(StringBuilder word) {
        return !word.toString().isBlank();
    }

    private boolean isLastLetterInWord(String[] arrayOfStrings, int iteration) {
        return iteration == arrayOfStrings.length - 1;
    }
}