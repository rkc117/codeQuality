package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "codeQualityCheckRecord")
public class CodeQualityCheckRecord {

    @Id
    private String id;

    private List<GitHubUserCodeQuality> gitHubUserCodeQuality = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GitHubUserCodeQuality> getGitHubUserCodeQuality() {
        return gitHubUserCodeQuality;
    }

    public void setGitHubUserCodeQuality(List<GitHubUserCodeQuality> gitHubUserCodeQuality) {
        this.gitHubUserCodeQuality = gitHubUserCodeQuality;
    }
}

