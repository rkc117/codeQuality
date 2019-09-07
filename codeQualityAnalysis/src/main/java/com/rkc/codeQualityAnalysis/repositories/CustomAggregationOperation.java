package com.rkc.codeQualityAnalysis.repositories;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

public class CustomAggregationOperation implements AggregationOperation {
    private Document document;

    public CustomAggregationOperation(Document document) {
        this.document = document;
    }

    @Override
    public Document toDocument(AggregationOperationContext context) {
        return context.getMappedObject(document);
    }
}
