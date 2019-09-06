package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.CheckStyle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckStylesRepository extends MongoRepository<CheckStyle,String> {

}
