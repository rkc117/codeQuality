package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.GitHubUserCodeQuality;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GitHubUserCodeQualityRepository extends MongoRepository<GitHubUserCodeQuality,String> {
    List<GitHubUserCodeQuality> findAllByRequestId(String requestId);
}
