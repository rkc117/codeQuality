package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.parsers.Parsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PMDService {

    @Autowired
    private Parsers parsers;

    private static String PMD = "/home/rkc/Briefcase/spotbugs/pmd/pmd-bin-6.17.0/bin/run.sh pmd -d  %s -f text -R rulesets/java/quickstart.xml";

    public void runThroughPMD(List<String> filePaths, String gitHubUserName, String requestId) {

        for (String filePath : filePaths) {

            try {

                String command = String.format(PMD, filePath);

                Process process = Runtime.getRuntime().exec(command);

                parsers.parseAndSave(process.getInputStream(), "pmd", gitHubUserName,requestId);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ResponseEntity<?> getPMD(String requestId) {
        // db.pmd.aggregate([{"$match":{"requestId":"5d72b264216d9153589bad28"}},{"$group":{"_id":"$fileName","message":{"$push":{"line":"$lineNumber","message":"$message"}}}}])

        return null;
    }
}
