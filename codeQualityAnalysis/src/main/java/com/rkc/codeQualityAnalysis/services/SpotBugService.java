package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SpotBugService {

    private String SPOT_BUGS = "/home/rkc/Briefcase/spotbugs/spotbugs-4.0.0-beta3/bin/spotbugs2 -textui %s ";

    public void runThroughSpotBug(String jarFilePath,String userName) {

        try {
            String command = String.format(SPOT_BUGS, jarFilePath);
            Process process = Runtime.getRuntime().exec(command);
            Utils.printStream(process.getInputStream());
            Utils.printStream(process.getErrorStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

