package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CodeQualityCheckRecord;
import com.rkc.codeQualityAnalysis.payloads.CodeQualityCheckRequest;
import com.rkc.codeQualityAnalysis.payloads.KeyValue;
import com.rkc.codeQualityAnalysis.repositories.CodeQualityCheckRecordRepository;
import com.rkc.codeQualityAnalysis.tasks.CodeQualityCheckerTask;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CodeQualityCheckerService {

    @Autowired
    private CodeQualityCheckRecordRepository codeQualityCheckRecordRepository;

    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ResponseEntity<?> check(CodeQualityCheckRequest codeQualityCheckRequest) {


        String requestId = ObjectId.get().toString();

        CodeQualityCheckRecord codeQualityCheckRecord = new CodeQualityCheckRecord();
        codeQualityCheckRecord.setId(requestId);

        codeQualityCheckRecordRepository.save(codeQualityCheckRecord);

        for (KeyValue keyValue : codeQualityCheckRequest.getKeyValues()) {

            CodeQualityCheckerTask codeQualityCheckerTask = new CodeQualityCheckerTask(requestId, keyValue.getValue());
            executorService.execute(codeQualityCheckerTask);

        }

        return ResponseEntity.accepted().body(new HashMap<>() {{
            put("data", requestId.toString());
            put("message", "success");
            put("status", 200);
            put("info", new HashMap<>());
        }});
    }
}
