package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.PMD;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PMDRepository extends MongoRepository<PMD,String> {
}
