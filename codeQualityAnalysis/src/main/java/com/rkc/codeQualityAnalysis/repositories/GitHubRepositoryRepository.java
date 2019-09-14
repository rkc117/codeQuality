package com.rkc.codeQualityAnalysis.repositories;

import com.rkc.codeQualityAnalysis.models.GitHubRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GitHubRepositoryRepository extends MongoRepository<GitHubRepository,String> {
}
