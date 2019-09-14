package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.CyclomaticComplexity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CyclomaticComplexityRepository extends MongoRepository<CyclomaticComplexity,String> {
    List<CyclomaticComplexity> findAllByType(String type);

    List<CyclomaticComplexity> findByRequestIdAndType(String requestId, String type);
}
