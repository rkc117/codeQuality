package com.rkc.codeQualityAnalysis.controllers;


import com.rkc.codeQualityAnalysis.services.*;
import com.rkc.codeQualityAnalysis.utils.JarCreater;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class EntryController {

    @Autowired
    private CheckStylesService checkStylesService;
    @Autowired
    private PMDService pmdService;
    @Autowired
    private CPDService cpdService;
    @Autowired
    private SpotBugService spotBugService;
    @Autowired
    private JaCoCoService jaCoCoService;
    @Autowired
    private MethodCallGraphService methodCallGraphService;

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestBody List<String> gitRepoUrls) throws Exception {

        String gitHubUserName;
        String s = gitRepoUrls.get(0);

        String substring = s.substring("https://github.com".length(), s.length());
        String[] split = substring.split("/");
        gitHubUserName=split[1];
        String path = "/home/rkc/Briefcase/spotbugs/gitHubDownloadedRepos/" + split[1];

        RequestContextHolder.getRequestAttributes().setAttribute("JavaFilePath",path,0);

        if (Files.notExists(Path.of(path))) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            Git call = Git.cloneRepository()
                    .setDirectory(file)
                    .setBranch("master")
                    .setURI(s).call();

            String identifier = call.getRepository().getIdentifier();
        }

        File folder = new File(path);

        File[] files = folder.listFiles();

        HashMap<String, String> filePathToFileName = new HashMap<>();

        //collect all java files in a map
        for (File file : files) {
            if (file.isFile()) {

                if (file.getName().endsWith(".java")) {
                    filePathToFileName.put(file.getAbsolutePath(), file.getName());
                }
            } else {
                copyJavaFileFromFolder(file, filePathToFileName);
            }
        }

        filePathToFileName.forEach((key, value) -> {
            System.out.println(value + " -> " + key);
        });

        //Checkstyle
        checkStylesService.checkStyles(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()),gitHubUserName);

        //PMD
        pmdService.runThroughPMD(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()),gitHubUserName);

        //CPD
        cpdService.runThroughCPD(path,gitHubUserName);

        //cyclomatic and halstead complexity

       // String jarFilePath = JarCreater.createJar(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()),gitHubUserName,"/tmp/");

        //FindBugs
        //spotBugService.runThroughSpotBug(jarFilePath,gitHubUserName);

        //MethodCall Graph Service
        //methodCallGraphService.generateMethodCallGraph(jarFilePath,gitHubUserName);

        //JaCoCo
       // jaCoCoService.runThroughJaCoCo();

        return ResponseEntity.ok("success");
    }

    private void copyJavaFileFromFolder(File folder, HashMap<String, String> filePathToFileName) {
        File[] files = folder.listFiles();

        //collect all java files in a map
        for (File file : files) {
            if (file.isFile()) {

                if (file.getName().endsWith(".java")) {
                    filePathToFileName.put(file.getAbsolutePath(), file.getName());
                }
            } else {
                copyJavaFileFromFolder(file, filePathToFileName);
            }
        }
    }

    public int countNumberOfLineInFile(File f1) throws IOException {
        int linecount = 0;            //Intializing linecount as zero
        try (FileReader fr = new FileReader(f1)) {
            BufferedReader br = new BufferedReader(fr);    //Creation of File Reader object
            String s;
            while ((s = br.readLine()) != null)    //Reading Content from the file line by line
            {
                linecount++;               //For each line increment linecount by one
            }
        }  //Creation of File Reader object

        return linecount;
    }
}
