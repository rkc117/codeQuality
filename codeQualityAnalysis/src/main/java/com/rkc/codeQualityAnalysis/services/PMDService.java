package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CheckStyle;
import com.rkc.codeQualityAnalysis.models.Files;
import com.rkc.codeQualityAnalysis.models.PMD;
import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.repositories.CustomAggregationOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class PMDService {

    @Autowired
    private Parsers parsers;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static String PMD = "/home/rkc/Briefcase/spotbugs/pmd/pmd-bin-6.17.0/bin/run.sh pmd -d  %s -f text -R rulesets/java/quickstart.xml";

    public void runThroughPMD(List<String> filePaths, String gitHubUserName, String requestId, List<Files> files) {

        for (String filePath : filePaths) {

            try {

                String command = String.format(PMD, filePath);

                Process process = Runtime.getRuntime().exec(command);

                parsers.parseAndSave(process.getInputStream(), "pmd", gitHubUserName,requestId,files);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // db.pmd.aggregate([{"$match":{"requestId":"5d72b264216d9153589bad28"}},{"$group":{"_id":"$fileName","message":{"$push":{"line":"$lineNumber","message":"$message"}}}}])
    public List<Document> getPMD(String requestId,String userName) {

        Document matchOperation = new Document();
        Document matchCriteria = new Document();
        matchCriteria.put("requestId", requestId);
        if(userName!= null){
            matchCriteria.put("userName", userName);
        }

        matchOperation.put("$match",matchCriteria );

        Document groupOperation = new Document();

        Document group = new Document();
        group.put("_id", "$fileName");

        Document push = new Document();
        push.put("lineNumber", "$lineNumber");
        push.put("message", "$message");

        group.put("pmdAt", new Document("$push", push));

        Document totalLines = new Document();
        totalLines.put("$first", "$totalLines");

        group.put("totalLines", totalLines);

        Document totalCounts = new Document();
        totalCounts.put("$sum", 1);

        group.put("totalCounts", totalCounts);

        groupOperation.put("$group",group);

        TypedAggregation<Document> agg = newAggregation(Document.class,
                new CustomAggregationOperation(matchOperation), new CustomAggregationOperation(groupOperation)
        );

        AggregationResults<Document> aggregate = mongoTemplate.aggregate(agg, PMD.class, Document.class);
        return aggregate.getMappedResults();

    }
}
