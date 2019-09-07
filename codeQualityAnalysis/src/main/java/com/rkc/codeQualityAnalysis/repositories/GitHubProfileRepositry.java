package com.rkc.codeQualityAnalysis.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rkc.codeQualityAnalysis.models.GitHubProfile;

public interface GitHubProfileRepositry extends MongoRepository<GitHubProfile,String> {

	List<GitHubProfile> findAllByUserName(String userName);
	List<GitHubProfile> findAllByUserNameAndRepoName(String userName,String repoName);
}
