package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.Files;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FilesRepository extends MongoRepository<Files,String> {

    List<Files> findByRequestId(String requestId);
}
