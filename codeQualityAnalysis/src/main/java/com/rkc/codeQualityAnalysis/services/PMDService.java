package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PMDService {

    @Autowired
    private Parsers parsers;

    private static String PMD = "/home/rkc/Briefcase/spotbugs/pmd/pmd-bin-6.17.0/bin/run.sh pmd -d  %s -f text -R rulesets/java/quickstart.xml";

    public void runThroughPMD(List<String> filePaths,String userName) {

        for (String filePath : filePaths) {

            try {

                String command = String.format(PMD, filePath);

                System.out.println(command);

                Process process = Runtime.getRuntime().exec(command);

                //Utils.printStream(process.getInputStream());
                //Utils.printStream(process.getErrorStream());

                parsers.parseAndSave(process.getInputStream(), "pmd", userName);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
