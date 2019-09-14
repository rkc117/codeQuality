package com.rkc.codeQualityAnalysis.models;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gitHubRepository")
public class GitHubRepository {

    private String userName;
    private String repoName;
    private String repoUrl;
    private String language;
    private Float score;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
