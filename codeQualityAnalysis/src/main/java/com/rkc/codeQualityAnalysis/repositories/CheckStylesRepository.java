package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.CheckStyle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CheckStylesRepository extends MongoRepository<CheckStyle,String> {

    List<CheckStyle> findAllByRequestId(String requestId);

}
