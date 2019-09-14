package com.rkc.codeQualityAnalysis.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.rkc.codeQualityAnalysis.models.GitHubRepository;
import com.rkc.codeQualityAnalysis.repositories.GitHubRepositoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GitHubRepoFetechers {

    @Autowired
    private GitHubRepositoryRepository gitHubRepositoryRepository;

    private RestTemplate restTemplate = new RestTemplate();

    public void fetchGitHUbRepoDetails(String username, List<String> languages) {

        List<GitHubRepository> gitHubRepositories = new ArrayList<>();

        for (String language : languages) {

            //https://api.github.com/users/imaqsud/repos?q=language:Java
            //
            JsonNode jsonNode = restTemplate.getForObject(String.format("https://api.github.com/users/%s/repos?q=language:%s", username, language), JsonNode.class);

            if (jsonNode != null && jsonNode.isArray()) {
                jsonNode.forEach(jsonNode1 -> {
                    JsonNode repoName = jsonNode1.get("full_name");
                    JsonNode repoLanguage = jsonNode1.get("language");
                    JsonNode repoUrl = jsonNode1.get("clone_url");

                    GitHubRepository gitHubRepository = new GitHubRepository();
                    gitHubRepository.setUserName(username);
                    gitHubRepository.setLanguage(repoLanguage.asText());
                    gitHubRepository.setRepoUrl(repoUrl.asText());
                    gitHubRepository.setRepoName(repoName.asText());

                    gitHubRepositories.add(gitHubRepository);
                });
            }
        }

        gitHubRepositoryRepository.saveAll(gitHubRepositories);
    }
}
