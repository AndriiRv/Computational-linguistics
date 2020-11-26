package com.chnu.computationallinguistics.lab3.service;

import com.chnu.computationallinguistics.lab3.model.NGramModel;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class NGramServiceTest {

    private NGramService service;

    @BeforeTest
    public void setUp() {
        service = new NGramService();
    }

    @Test
    public void getRelevance_When_LengthOfGramIsOne() {
        double relevance = service.getRelevance(new NGramModel("лодка", "лодтка", 1));

        Assert.assertEquals(relevance, 0.9090909090909091);
    }

    @Test
    public void getRelevance_When_LengthOfGramIsTwo() {
        double relevance = service.getRelevance(new NGramModel("лодка", "лодтка", 2));

        Assert.assertEquals(relevance, 0.6666666666666666);
    }

    @Test
    public void getRelevance_When_LengthOfGramIsThree() {
        double relevance = service.getRelevance(new NGramModel("лодка", "лодтка", 3));

        Assert.assertEquals(relevance, 0.2857142857142857);
    }
}