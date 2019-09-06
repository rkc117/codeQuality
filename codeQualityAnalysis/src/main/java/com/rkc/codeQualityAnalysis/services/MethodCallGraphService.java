package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MethodCallGraphService {

    private static String METHOD_CALL_GRAPH = "java -jar /home/rkc/Briefcase/spotbugs/java-callgraph-master/javacg-0.1-SNAPSHOT-static.jar %s";

    public void generateMethodCallGraph(String jarFilePath,String userName){

        try {
            String command = String.format(METHOD_CALL_GRAPH, jarFilePath);

            System.out.println(command);

            Process process = Runtime.getRuntime().exec(command);

            Utils.printStream(process.getInputStream());
            Utils.printStream(process.getErrorStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
