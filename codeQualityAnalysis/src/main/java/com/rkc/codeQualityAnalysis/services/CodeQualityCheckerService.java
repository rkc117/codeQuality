package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CodeQualityCheckRecord;
import com.rkc.codeQualityAnalysis.models.CyclomaticComplexity;
import com.rkc.codeQualityAnalysis.models.GitHubUserCodeQuality;
import com.rkc.codeQualityAnalysis.payloads.CodeQualityCheckRequest;
import com.rkc.codeQualityAnalysis.payloads.KeyValue;
import com.rkc.codeQualityAnalysis.repositories.CodeQualityCheckRecordRepository;
import com.rkc.codeQualityAnalysis.repositories.FilesRepository;
import com.rkc.codeQualityAnalysis.repositories.GitHubUserCodeQualityRepository;
import com.rkc.codeQualityAnalysis.tasks.CodeQualityCheckerTask;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CodeQualityCheckerService {

    @Autowired
    private CodeQualityCheckRecordRepository codeQualityCheckRecordRepository;
    @Autowired
    private CheckStylesService checkStylesService;
    @Autowired
    private PMDService pmdService;
    @Autowired
    private CPDService cpdService;
    @Autowired
    private CyclomaticService cyclomaticService;
    @Autowired
    private GitHubUserCodeQualityRepository gitHubUserCodeQualityRepository;
    @Autowired
    private FilesRepository filesRepository;
    @Autowired
    private ProfileService profileService;

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ResponseEntity<?> check(CodeQualityCheckRequest codeQualityCheckRequest) {


        String requestId = ObjectId.get().toString();

        CodeQualityCheckRecord codeQualityCheckRecord = new CodeQualityCheckRecord();
        codeQualityCheckRecord.setId(requestId);

        codeQualityCheckRecordRepository.save(codeQualityCheckRecord);

        for (String gitRepo : codeQualityCheckRequest.getGitRepos()) {

            CodeQualityCheckerTask codeQualityCheckerTask = new CodeQualityCheckerTask(requestId, gitRepo, new GitHubUserCodeQuality());
            executorService.execute(codeQualityCheckerTask);

        }

        return ResponseEntity.accepted().body(new HashMap<>() {{
            put("data", requestId);
            put("message", "success");
            put("status", 200);
            put("info", new HashMap<>());
        }});
    }

    public ResponseEntity<?> getResult(String requestId,String currentTime) {

        List<GitHubUserCodeQuality> allByRequestId = gitHubUserCodeQualityRepository.findAllByRequestId(requestId);
        allByRequestId.forEach(gitHubUserCodeQuality -> {

            List<Document> checkStyles = checkStylesService.getCheckStyles(gitHubUserCodeQuality.getRequestId(), gitHubUserCodeQuality.getUserName());

            float checkStyleScore = 0f;

            for (Document document : checkStyles) {
                Integer totalCounts = document.getInteger("totalCounts");
                Integer totalLines = Integer.parseInt(document.getString("totalLines"));
                checkStyleScore += (totalCounts * 1f) / (totalLines * 1f);

            }

            if (checkStyles.size() > 0) {
                checkStyleScore /= (checkStyles.size() * 1f);
            }

            float pmdScore = 0f;
            List<Document> pmds = pmdService.getPMD(gitHubUserCodeQuality.getRequestId(), gitHubUserCodeQuality.getUserName());

            for (Document document : pmds) {
                Integer totalCounts = document.getInteger("totalCounts");
                Integer totalLines = Integer.parseInt(document.getString("totalLines"));

                pmdScore += (totalCounts * 1f) / (totalLines * 1f);
            }

            if (pmds.size() > 0) {
                pmdScore /= (pmds.size() * 1f);
            }
            List<Document> cpds = cpdService.getCPD(gitHubUserCodeQuality.getRequestId(), gitHubUserCodeQuality.getUserName());

            float cpdsScore = 0f;

            for (Document document : cpds) {
                Integer totalCounts = document.getInteger("totalCounts");
                Integer totalLines = Integer.parseInt(document.getString("totalLines"));
                cpdsScore += (totalCounts * 1f) / (totalLines * 1f);
            }

            if (cpds.size() > 0) {
                cpdsScore /= (cpds.size() * 1f);
            }
            List<CyclomaticComplexity> file = cyclomaticService.getCyclomaticComplexity(requestId, "file");

            Float cyclomaticScore = 0f;

            for (CyclomaticComplexity cyclomaticComplexity : file) {
                String avgCCN = cyclomaticComplexity.getAvgCCN();
                Float aFloat = Float.valueOf(avgCCN);
                cyclomaticScore += aFloat;
            }

            cyclomaticScore = (cyclomaticScore / (file.size() * 1f));

            System.out.println(checkStyleScore + " -> " + pmdScore + " -> " + cpdsScore + " -> " + cyclomaticScore+"->" +currentTime);

            Float overallScore = 0f;
            int i = cyclomaticScore.intValue();
            if (i <= 10) {
                overallScore += 30f;
            }
            if (i > 10 && i <= 20) {
                overallScore += 12f;
            }

            overallScore += ((1.0f - pmdScore) * 0.25f) * 100f;

            overallScore += ((1.0f - cpdsScore) * 0.25f) * 100f;

            checkStyleScore = 1.0f - checkStyleScore;

            overallScore += checkStyleScore * 100 * 0.1f;

            String repoName =null;

            String[] split = gitHubUserCodeQuality.getGitHubRepoUrl().split("/");
            String s = split[split.length - 1];
            String q[] = s.split("\\.");
            repoName=q[0];
            float gitHubProfileInfo = profileService.getGitHubProfileInfo(gitHubUserCodeQuality.getUserName(), repoName);

            overallScore+=gitHubProfileInfo*10f;

            gitHubUserCodeQuality.setCheckstyles(checkStyles);
            gitHubUserCodeQuality.setTotalLines(gitHubUserCodeQuality.getTotalLines());
            gitHubUserCodeQuality.setTotalNumberFiles(gitHubUserCodeQuality.getTotalNumberFiles());
            gitHubUserCodeQuality.setPmds(pmds);
            gitHubUserCodeQuality.setCpds(cpds);
            gitHubUserCodeQuality.setScore(overallScore);
            gitHubUserCodeQuality.setCyclomatics(new HashMap<>() {{
                put("file", file);
            }});
        });


        allByRequestId.sort(Comparator.comparing(GitHubUserCodeQuality::getScore));

        return ResponseEntity.ok(new HashMap<>() {{
            put("data", allByRequestId);
            put("status", 200);
            put("message", "success");
        }});

    }

    public ResponseEntity<?> getFiles(String requestId) {
        return ResponseEntity.ok(new HashMap<>() {{

            put("data", filesRepository.findByRequestId(requestId));
            put("status", 200);
            put("message", "success");
        }});
    }
}
