package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CPD;
import com.rkc.codeQualityAnalysis.models.Files;
import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.repositories.CPDRepository;
import com.rkc.codeQualityAnalysis.repositories.CustomAggregationOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class CPDService {

    @Autowired
    private Parsers parsers;

    @Autowired
    private CPDRepository cpdRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static String CPD = "/home/rkc/Briefcase/spotbugs/pmd/pmd-bin-6.17.0/bin/run.sh cpd --minimum-tokens 100 --language java  --files %s";

    public void runThroughCPD(String path, String gitHubUserName, String requestId, List<Files> files) {

        try {

            String command = String.format(CPD, path);

            Process process = Runtime.getRuntime().exec(command);

            parsers.parseCPD(process.getInputStream(), gitHubUserName, requestId,files);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //db.cpd.aggregate([{"$match":{"_id":ObjectId("5d72b531216d9153589bbe99")}},{"$unwind":"$duplicateFiles"},{"$group":{"_id":"$duplicateFiles.fileName","duplicatedAt":{$push:{"line":"$duplicateFiles.lineNumber","code":"$code"}}}}]).pretty()
    public List<Document> getCPD(String requestId, String userName) {

        Document match = new Document();
        Document matchingCriteria = new Document("requestId", requestId);
        matchingCriteria.put("userName", userName);

        match.put("$match", matchingCriteria);

        Document unwind = new Document();
        unwind.put("$unwind", "$duplicateFiles");

        Document groupObject = new Document();
        Document group = new Document();
        groupObject.put("$group", group);

        group.put("_id", "$duplicateFiles.fileName");

        Document push = new Document();
        push.put("lineNumber", "$duplicateFiles.lineNumber");
        push.put("message", "$code");

        Document totalLines = new Document();
        totalLines.put("$first", "$duplicateFiles.totalLines");


        group.put("totalLines", totalLines);

        Document totalCounts = new Document();
        totalCounts.put("$sum", 1);

        group.put("totalCounts", totalCounts);

        group.put("duplicatedAt", new Document("$push", push));


        TypedAggregation<Document> agg = newAggregation(Document.class,
                new CustomAggregationOperation(match), new CustomAggregationOperation(unwind), new CustomAggregationOperation(groupObject)
        );

        AggregationResults<Document> aggregate = mongoTemplate.aggregate(agg, CPD.class, Document.class);
        return aggregate.getMappedResults();
    }

}
