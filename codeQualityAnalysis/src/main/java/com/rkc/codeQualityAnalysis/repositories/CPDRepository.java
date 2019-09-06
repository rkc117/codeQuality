package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.CPD;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CPDRepository extends MongoRepository<CPD,String> {
}
