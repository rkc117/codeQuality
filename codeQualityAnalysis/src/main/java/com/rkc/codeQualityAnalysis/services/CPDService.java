package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CPDService {

    @Autowired
    private Parsers parsers;

    private static String CPD = "/home/rkc/Briefcase/spotbugs/pmd/pmd-bin-6.17.0/bin/run.sh cpd --minimum-tokens 40 --language java  --files %s";

    public void runThroughCPD(String folderPath, String userName) {

        try {

            String command = String.format(CPD, folderPath);

            System.out.println(command);

            Process process = Runtime.getRuntime().exec(command);

            //Utils.printStream(process.getInputStream());
            //Utils.printStream(process.getErrorStream());

            parsers.parseCPD(process.getInputStream(), userName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
