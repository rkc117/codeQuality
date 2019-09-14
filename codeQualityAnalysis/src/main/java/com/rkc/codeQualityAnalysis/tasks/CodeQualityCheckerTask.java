package com.rkc.codeQualityAnalysis.tasks;

import com.rkc.codeQualityAnalysis.factories.SpringFactory;
import com.rkc.codeQualityAnalysis.models.GitHubUserCodeQuality;
import com.rkc.codeQualityAnalysis.repositories.FilesRepository;
import com.rkc.codeQualityAnalysis.repositories.GitHubUserCodeQualityRepository;
import com.rkc.codeQualityAnalysis.services.*;
import com.rkc.codeQualityAnalysis.utils.JarCreater;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeQualityCheckerTask implements Runnable {

    private String requestId;
    private String gitRepositoryPath;
    private GitHubUserCodeQuality gitHubUserCodeQuality;
    private String userName;

    public CodeQualityCheckerTask(String requestId, String gitRepositoryPath, GitHubUserCodeQuality gitHubUserCodeQuality) {
        this.requestId = requestId;
        this.gitRepositoryPath = gitRepositoryPath;
        this.gitHubUserCodeQuality = gitHubUserCodeQuality;
    }

    public CodeQualityCheckerTask(String requestId, String gitRepositoryPath, GitHubUserCodeQuality gitHubUserCodeQuality, String userName) {
        this.requestId = requestId;
        this.gitRepositoryPath = gitRepositoryPath;
        this.gitHubUserCodeQuality = gitHubUserCodeQuality;
        this.userName = userName;
    }

    @Override
    public void run() {

        CheckStylesService checkStylesService = SpringFactory.getContext().getBean(CheckStylesService.class);
        PMDService pmdService = SpringFactory.getContext().getBean(PMDService.class);
        CPDService cpdService = SpringFactory.getContext().getBean(CPDService.class);
        CyclomaticService cyclomaticService = SpringFactory.getContext().getBean(CyclomaticService.class);
        SpotBugService spotBugService = SpringFactory.getContext().getBean(SpotBugService.class);
        FilesRepository filesRepository = SpringFactory.getContext().getBean(FilesRepository.class);
        GitHubUserCodeQualityRepository gitHubUserCodeQualityRepository = SpringFactory.getContext().getBean(GitHubUserCodeQualityRepository.class);
        ProfileService profileService = SpringFactory.getContext().getBean(ProfileService.class);


        //if username is not null get all java repositories
        if(userName!= null){

        }



        String s = gitRepositoryPath;

        String substring = s.substring("https://github.com".length(), s.length());
        String[] split = substring.split("/");
        String gitHubUserName = split[1];
        String path = "/home/rkc/Briefcase/spotbugs/gitHubDownloadedRepos/" + split[1];

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

        List<com.rkc.codeQualityAnalysis.models.Files> filesToSave = new ArrayList<>();
        long totalLinesInRepos = 0l;

        //collect all java files in a map

        RecursionData recursionData = new RecursionData();
        recursionData.files = filesToSave;
        recursionData.totalLines = 0l;
        copyJavaFileFromFolder(folder, filePathToFileName, recursionData);

        //save all files path and line Number
        filesRepository.saveAll(filesToSave);
        gitHubUserCodeQuality.setUserName(gitHubUserName);
        gitHubUserCodeQuality.setRequestId(requestId);
        gitHubUserCodeQuality.setGitHubRepoUrl(gitRepositoryPath);
        gitHubUserCodeQuality.setTotalLines(String.valueOf(recursionData.totalLines));
        gitHubUserCodeQuality.setTotalNumberFiles(String.valueOf(filesToSave.size()));

        if (filesToSave.size() > 0) {
            gitHubUserCodeQuality.setAverageLines(String.valueOf(recursionData.totalLines / filesToSave.size()));
        } else {
            gitHubUserCodeQuality.setAverageLines("0");
        }

        gitHubUserCodeQualityRepository.save(gitHubUserCodeQuality);

        //Checkstyle
        checkStylesService.checkStyles(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()), gitHubUserName, requestId, filesToSave);

        //PMD
        pmdService.runThroughPMD(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()), gitHubUserName, requestId, filesToSave);

        //CPD
        cpdService.runThroughCPD(path, gitHubUserName, requestId, filesToSave);

        //cyclomatic
        cyclomaticService.generateCyclomaticComplexity(path, gitHubUserName, requestId);

        //calculate github profile score
        profileService.getGitHubProfileInfo(gitHubUserName, null);

        // halstead complexity

       /* String jarFilePath = null;
        try {
            jarFilePath = JarCreater.createJar(filePathToFileName.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList()), gitHubUserName, "/tmp/");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //FindBugs
        // spotBugService.runThroughSpotBug(jarFilePath, gitHubUserName,requestId);

        //MethodCall Graph Service
        //methodCallGraphService.generateMethodCallGraph(jarFilePath,gitHubUserName);
    }

    private void copyJavaFileFromFolder(File folder, HashMap<String, String> filePathToFileName, RecursionData recursionData) {
        File[] files = folder.listFiles();

        //collect all java files in a map
        for (File file : files) {
            if (file.isFile()) {

                if (file.getName().endsWith(".java")) {
                    try {
                        com.rkc.codeQualityAnalysis.models.Files files1 = new com.rkc.codeQualityAnalysis.models.Files();
                        files1.setId(file.getAbsolutePath());
                        files1.setName(file.getName());
                        files1.setRequestId(requestId);
                        int i = countNumberOfLineInFile(file);
                        files1.setTotalNumberLines(String.valueOf(i));
                        recursionData.files.add(files1);
                        recursionData.totalLines += i;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    filePathToFileName.put(file.getAbsolutePath(), file.getName());
                }
            } else {
                copyJavaFileFromFolder(file, filePathToFileName, recursionData);
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

    static class RecursionData {
        Long totalLines = 0l;
        List<com.rkc.codeQualityAnalysis.models.Files> files;


    }

}
