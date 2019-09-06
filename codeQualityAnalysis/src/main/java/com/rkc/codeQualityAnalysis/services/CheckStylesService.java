package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CheckStyle;
import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.repositories.CheckStylesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CheckStylesService {

    @Autowired
    private Parsers parsers;
    @Autowired
    private CheckStylesRepository checkStylesRepository;


    private String CHECKSTYLE = "java -jar /home/rkc/Briefcase/spotbugs/checkStyle/checkstyle-8.23-all.jar -c google_checks.xml %s";

    public void checkStyles(List<String> filePaths, String gitHubUserName, String requestId) {

        for (String filePath : filePaths) {

            try {

                String command = String.format(CHECKSTYLE, filePath);

                Process process = Runtime.getRuntime().exec(command);

                parsers.parseAndSave(process.getInputStream(), "checkstyles", gitHubUserName, requestId);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public ResponseEntity<?> getCheckStyles(String requestId) {

        //db.checkstyles.aggregate([{"$match":{"requestId":"5d72b264216d9153589bad28"}},{"$group":{"_id":"$fileName","results":{"$push":{"rowNumber":"$rowNumber","colNumber":"$colNumber","meaagse":"$checkstylesMessage"}}}}])

        List<CheckStyle> allByRequestId = checkStylesRepository.findAllByRequestId(requestId);
        System.out.println(allByRequestId.size());
        return null;
    }
}
