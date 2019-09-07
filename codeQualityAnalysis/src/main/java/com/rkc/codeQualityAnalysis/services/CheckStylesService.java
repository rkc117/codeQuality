package com.rkc.codeQualityAnalysis.services;

import com.rkc.codeQualityAnalysis.models.CPD;
import com.rkc.codeQualityAnalysis.models.CheckStyle;
import com.rkc.codeQualityAnalysis.parsers.Parsers;
import com.rkc.codeQualityAnalysis.repositories.CheckStylesRepository;
import com.rkc.codeQualityAnalysis.repositories.CustomAggregationOperation;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class CheckStylesService {

    @Autowired
    private Parsers parsers;
    @Autowired
    private CheckStylesRepository checkStylesRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    private String CHECKSTYLE = "java -jar /home/rkc/Briefcase/spotbugs/checkStyle/checkstyle-8.23-all.jar -c google_checks.xml %s";

    public void checkStyles(List<String> filePaths, String gitHubUserName, String requestId) {

        for (String filePath : filePaths) {

            try {

                String command = String.format(CHECKSTYLE, filePath);

                Process process = Runtime.getRuntime().exec(command);

                parsers.parseAndSave(process.getInputStream(), "checkstyles", gitHubUserName, requestId);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //db.checkstyles.aggregate([{"$match":{"requestId":"5d72b264216d9153589bad28"}},{"$group":{"_id":"$fileName","results":{"$push":{"rowNumber":"$rowNumber","colNumber":"$colNumber","meaagse":"$checkstylesMessage"}}}}])
    public ResponseEntity<?> getCheckStyles(String requestId) {

        Document matchOperation = new Document();
        matchOperation.put("$match", new Document("requestId", requestId));

        Document groupOperation = new Document();

        Document group = new Document();
        group.put("_id", "$fileName");

        Document push = new Document();
        push.put("rowNumber", "$rowNumber");
        push.put("colNumber", "$colNumber");
        push.put("meaagse", "$checkstylesMessage");

        group.put("results", new Document("$push", push));
        groupOperation.put("$group", group);

        TypedAggregation<Document> agg = newAggregation(Document.class,
                new CustomAggregationOperation(matchOperation), new CustomAggregationOperation(groupOperation)
        );

        AggregationResults<Document> aggregate = mongoTemplate.aggregate(agg, CheckStyle.class, Document.class);
        return ResponseEntity.ok(new HashMap<>() {{
            put("data", aggregate.getMappedResults());
            put("message", "success");
            put("status", 200);
        }});
    }
}
