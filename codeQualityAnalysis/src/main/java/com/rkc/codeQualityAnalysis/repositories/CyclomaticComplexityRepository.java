package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.CyclomaticComplexity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CyclomaticComplexityRepository extends MongoRepository<CyclomaticComplexity,String> {

}
