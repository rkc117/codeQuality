package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.parsers.Parsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CyclomaticService {

    @Autowired
    private Parsers parsers;

    private static String LIZARD = "/home/rkc/anaconda3/bin/lizard -l java %s";

    public void generateCyclomaticComplexity(String folderPath,String userName){

        try {

            String command = String.format(LIZARD, folderPath);

            System.out.println(command);

            Process process = Runtime.getRuntime().exec(command);

            parsers.parseCyclomatic(process.getInputStream(), userName);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
