package com.rkc.codeQualityAnalysis.tasks;

import com.rkc.codeQualityAnalysis.factories.SpringFactory;
import com.rkc.codeQualityAnalysis.services.*;
import com.rkc.codeQualityAnalysis.utils.JarCreater;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeQualityCheckerTask implements Runnable {

    private String requestId;
    private String gitRepositoryPath;

    public CodeQualityCheckerTask(String requestId, String gitRepositoryPath) {
        this.requestId = requestId;
        this.gitRepositoryPath = gitRepositoryPath;
    }

    @Override
    public void run() {

        CheckStylesService checkStylesService = SpringFactory.getContext().getBean(CheckStylesService.class);
        PMDService pmdService = SpringFactory.getContext().getBean(PMDService.class);
        CPDService cpdService = SpringFactory.getContext().getBean(CPDService.class);
        CyclomaticService cyclomaticService = SpringFactory.getContext().getBean(CyclomaticService.class);
        SpotBugService spotBugService = SpringFactory.getContext().getBean(SpotBugService.class);

        String s = gitRepositoryPath;

        String substring = s.substring("https://github.com".length(), s.length());
        String[] split = substring.split("/");
        String gitHubUserName = split[1];
        String path = "/home/rkc/Briefcase/spotbugs/gitHubDownloadedRepos/" + split[1];

      //  RequestContextHolder.getRequestAttributes().setAttribute("JavaFilePath", path, 0);

        if (Files.notExists(Path.of(path))) {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            Git call = null;
            try {
                call = Git.cloneRepository()
                        .setDirectory(file)
                        .setBranch("master")
                        .setURI(s).call();
            } catch (GitAPIException e) {
                e.printStackTrace();
            }

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

        //Checkstyle
        //checkStylesService.checkStyles(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()), gitHubUserName, requestId);

        //PMD
        //pmdService.runThroughPMD(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()), gitHubUserName,requestId);

        //CPD
       // cpdService.runThroughCPD(path, gitHubUserName,requestId);

        //cyclomatic
        cyclomaticService.generateCyclomaticComplexity(path, gitHubUserName,requestId);

        // halstead complexity

        String jarFilePath = null;
        try {
            jarFilePath = JarCreater.createJar(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()), gitHubUserName, "/tmp/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //FindBugs
       // spotBugService.runThroughSpotBug(jarFilePath, gitHubUserName,requestId);

        //MethodCall Graph Service
        //methodCallGraphService.generateMethodCallGraph(jarFilePath,gitHubUserName);
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
