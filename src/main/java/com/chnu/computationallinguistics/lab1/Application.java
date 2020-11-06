package com.chnu.computationallinguistics.lab1;

import com.chnu.computationallinguistics.lab1.model.ZipfsLaw;
import com.chnu.computationallinguistics.lab1.service.ZipfsLawService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

@SpringBootApplication
public class Application {
    private static ZipfsLawService service;

    public Application(ZipfsLawService service) {
        this.service = service;
    }

    public static void main(String... args) throws IOException {
        SpringApplication.run(Application.class, args);

        StringBuilder content = new StringBuilder();

        File file = new File("C:\\Users\\Andrii\\Desktop\\11.txt");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = bufferedReader.readLine()) != null){
                content.append(line);
            }
        }

        Map<String, ZipfsLaw> strings = service.calculateRank(content.toString());
        System.out.println(strings);
    }
}