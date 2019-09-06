package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.parsers.Parsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class CheckStylesService {

    @Autowired
    private Parsers parsers;

    private String CHECKSTYLE = "java -jar /home/rkc/Briefcase/spotbugs/checkStyle/checkstyle-8.23-all.jar -c google_checks.xml %s";

    public void checkStyles(List<String> filePaths,String userName) {

        for (String filePath : filePaths) {

            try {

                String command = String.format(CHECKSTYLE, filePath);

                System.out.println(command);

                Process process = Runtime.getRuntime().exec(command);

                //Utils.printStream(process.getInputStream());
                parsers.parseAndSave(process.getInputStream(), "checkstyles",userName);

                //Utils.printStream(process.getErrorStream());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}
