package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.repositories.CPDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CPDService {

    @Autowired
    private Parsers parsers;

    @Autowired
    private CPDRepository cpdRepository;

    private static String CPD = "/home/rkc/Briefcase/spotbugs/pmd/pmd-bin-6.17.0/bin/run.sh cpd --minimum-tokens 40 --language java  --files %s";

    public void runThroughCPD(String path, String gitHubUserName, String requestId) {

        try {

            String command = String.format(CPD, path);

            System.out.println(command);

            Process process = Runtime.getRuntime().exec(command);

            parsers.parseCPD(process.getInputStream(), gitHubUserName, requestId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<?> getCPD(String requestId) {


        return null;
    }
}
