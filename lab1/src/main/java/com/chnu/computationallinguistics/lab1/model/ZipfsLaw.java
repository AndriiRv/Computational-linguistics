package com.chnu.computationallinguistics.lab1.model;

public class ZipfsLaw {
    private int frequency;
    private int rank;

    public ZipfsLaw(int frequency, int rank) {
        this.frequency = frequency;
        this.rank = rank;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}