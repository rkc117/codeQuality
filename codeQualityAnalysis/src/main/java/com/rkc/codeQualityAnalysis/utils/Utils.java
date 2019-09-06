package com.rkc.codeQualityAnalysis.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

    public static void printStream(InputStream stream) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(stream));


        StringBuilder builder = new StringBuilder();

        String line = null;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append(System.getProperty("line.separator"));
        }

        String result = builder.toString();

        System.out.println(result);
    }
}
