package com.rkc.codeQualityAnalysis.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JarCreater {

    private static String CREATE_JAR = "jar cf %s %s";

    private static  String COMPILE_JAVA_FILES = "javac -d %s %s";

    public static String createJar(List<String> javaFilePaths, String jarFileName, String jarFileDirectory) throws IOException{

        File folder = new File(jarFileDirectory + jarFileName);

        if(folder.exists()){
            folder.delete();
            folder = new File(jarFileDirectory + jarFileName);
            folder.mkdirs();
        }else{
            folder.mkdirs();
        }

        //compile java files
        for (String javaFilePath : javaFilePaths) {
            try {
                String command = String.format(COMPILE_JAVA_FILES, folder.getAbsolutePath().toString(), javaFilePath);
                Process process = Runtime.getRuntime().exec(command);
                Utils.printStream(process.getInputStream());
                Utils.printStream(process.getErrorStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        folder.setExecutable(true, true);

        //create jar from above folder
        // create class  file folder
        File classFilePath = new File(jarFileDirectory+jarFileName+"classFilePath/");
        if(!classFilePath.exists()){
            System.out.println(classFilePath.mkdirs());
        }

        //create jar
        StringBuilder commandBuilder = new StringBuilder(String.format(CREATE_JAR, classFilePath.getAbsolutePath()+"/"+jarFileName + ".jar",jarFileDirectory+jarFileName));

        try {

            Process process = Runtime.getRuntime().exec(commandBuilder.toString());

            Utils.printStream(process.getInputStream());
            Utils.printStream(process.getErrorStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

     //   folder.delete();

        return classFilePath.getAbsolutePath()+"/"+jarFileName + ".jar";
    }
}
