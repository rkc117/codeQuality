package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CodeQualityCheckRecord;
import com.rkc.codeQualityAnalysis.models.GitHubUserCodeQuality;
import com.rkc.codeQualityAnalysis.payloads.CodeQualityCheckRequest;
import com.rkc.codeQualityAnalysis.payloads.KeyValue;
import com.rkc.codeQualityAnalysis.repositories.CodeQualityCheckRecordRepository;
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

    public ResponseEntity<?> getResult(String requestId) {

        List<GitHubUserCodeQuality> allByRequestId = gitHubUserCodeQualityRepository.findAllByRequestId(requestId);
        allByRequestId.forEach(gitHubUserCodeQuality -> {

            List<Document> checkStyles = checkStylesService.getCheckStyles(gitHubUserCodeQuality.getRequestId(), gitHubUserCodeQuality.getUserName());
            List<Document> pmds = pmdService.getPMD(gitHubUserCodeQuality.getRequestId(), gitHubUserCodeQuality.getUserName());
            List<Document> cpds = cpdService.getCPD(gitHubUserCodeQuality.getRequestId(), gitHubUserCodeQuality.getUserName());
            gitHubUserCodeQuality.setCheckstyles(checkStyles);
            gitHubUserCodeQuality.setPmds(pmds);
            gitHubUserCodeQuality.setCpds(cpds);
            gitHubUserCodeQuality.setScore(getRandomScore(0,100));

        });


        allByRequestId.sort(Comparator.comparing(GitHubUserCodeQuality::getScore));

        return ResponseEntity.ok(new HashMap<>() {{
            put("data", allByRequestId);
            put("status", 200);
            put("message", "success");
        }});

    }

    private Float getRandomScore(int i, int i1) {
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;

        return result + 0.00001f;
    }
}
