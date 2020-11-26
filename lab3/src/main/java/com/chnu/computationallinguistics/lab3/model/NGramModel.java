package com.chnu.computationallinguistics.lab3.model;

public class NGramModel {
    private String firstString;  //S1
    private String secondString; //S2
    private int lengthOfGram;    //N

    public NGramModel(String firstString, String secondString, int lengthOfGram) {
        this.firstString = firstString;
        this.secondString = secondString;
        this.lengthOfGram = lengthOfGram;
    }

    public String getFirstString() {
        return firstString;
    }

    public void setFirstString(String firstString) {
        this.firstString = firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public void setSecondString(String secondString) {
        this.secondString = secondString;
    }

    public int getLengthOfGram() {
        return lengthOfGram;
    }

    public void setLengthOfGram(int lengthOfGram) {
        this.lengthOfGram = lengthOfGram;
    }
}