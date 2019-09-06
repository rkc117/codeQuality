package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.CodeQualityCheckRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeQualityCheckRecordRepository extends MongoRepository<CodeQualityCheckRecord,String> {
}
