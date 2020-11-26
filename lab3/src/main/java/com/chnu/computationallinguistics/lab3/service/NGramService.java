package com.chnu.computationallinguistics.lab3.service;

import com.chnu.computationallinguistics.lab3.model.NGramModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NGramService {
    private int nGramMatch1;
    private int nGramMatch2;
    private int nGramCount1;
    private int nGramCount2;

    public double getRelevance(NGramModel nGramModel) {
        String firstString = nGramModel.getFirstString().toLowerCase().replaceAll("[^а-яa-z0-9]", "");
        String secondString = nGramModel.getSecondString().toLowerCase().replaceAll("[^а-яa-z0-9]", "");
        int lengthOfGram = nGramModel.getLengthOfGram();

        nGramMatch1 = getNGramMatch(firstString, secondString, lengthOfGram);
        nGramMatch2 = getNGramMatch(secondString, firstString, lengthOfGram);

        nGramCount1 = getNGramCount(firstString, lengthOfGram);
        nGramCount2 = getNGramCount(secondString, lengthOfGram);

        return (double) (nGramMatch1 + nGramMatch2) / (nGramCount1 + nGramCount2);
    }

    public String getRelevanceTwoStr() {
        String result1 = String.valueOf((double) nGramMatch1 / nGramCount2 * 100);
        String result2 = String.valueOf((double) nGramMatch2 / nGramCount1 * 100);

        return result1 + " " + result2;
    }

    private int getNGramMatch(String str1, String str2, int lengthOfGram) {
        char[] str1CharsArray = str1.toCharArray();
        char[] str2CharsArray = str2.toCharArray();

        List<String> partOfStr1 = getPartOfStr(str1, str1CharsArray, lengthOfGram);
        List<String> partOfStr2 = getPartOfStr(str2, str2CharsArray, lengthOfGram);

        int countOfMatch = 0;

        for (String part : partOfStr1) {
            if (partOfStr2.contains(part)) {
                countOfMatch++;
            }
        }
        return countOfMatch;
    }

    private List<String> getPartOfStr(String str, char[] strCharsArray, int lengthOfGram) {
        List<String> result = new ArrayList<>();
        int temp = 0;
        int nextChar = lengthOfGram;

        for (int i = 0; i < strCharsArray.length; i++) {
            if (nextChar <= strCharsArray.length) {
                result.add(str.substring(temp, nextChar));
                temp++;
                nextChar++;
            }
        }
        return result;
    }

    private int getNGramCount(String str, int lengthOfGram) {
        return (str.length() - lengthOfGram + 1);
    }
}