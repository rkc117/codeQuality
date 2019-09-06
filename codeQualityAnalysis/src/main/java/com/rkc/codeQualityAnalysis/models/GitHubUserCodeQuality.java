package com.rkc.codeQualityAnalysis.models;

public class GitHubUserCodeQuality {

    private String id;
    private String userName;
    private String gitHubRepoUrl;
    private String totalLines;
    private String averageLines;
    private String averageMaintainability;
    private String linesOfCodeUrl;
    private String maintainabilityUrl;
    private String filesUrl;
    private String checkStyleUrl;
    private String pmdUrl;
    private String cpdUrl;
    private String cyclomaticUrl;
    private String spotBugsUrl;
    private Float score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGitHubRepoUrl() {
        return gitHubRepoUrl;
    }

    public void setGitHubRepoUrl(String gitHubRepoUrl) {
        this.gitHubRepoUrl = gitHubRepoUrl;
    }

    public String getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(String totalLines) {
        this.totalLines = totalLines;
    }

    public String getAverageLines() {
        return averageLines;
    }

    public void setAverageLines(String averageLines) {
        this.averageLines = averageLines;
    }

    public String getAverageMaintainability() {
        return averageMaintainability;
    }

    public void setAverageMaintainability(String averageMaintainability) {
        this.averageMaintainability = averageMaintainability;
    }

    public String getLinesOfCodeUrl() {
        return linesOfCodeUrl;
    }

    public void setLinesOfCodeUrl(String linesOfCodeUrl) {
        this.linesOfCodeUrl = linesOfCodeUrl;
    }

    public String getMaintainabilityUrl() {
        return maintainabilityUrl;
    }

    public void setMaintainabilityUrl(String maintainabilityUrl) {
        this.maintainabilityUrl = maintainabilityUrl;
    }

    public String getFilesUrl() {
        return filesUrl;
    }

    public void setFilesUrl(String filesUrl) {
        this.filesUrl = filesUrl;
    }

    public String getCheckStyleUrl() {
        return checkStyleUrl;
    }

    public void setCheckStyleUrl(String checkStyleUrl) {
        this.checkStyleUrl = checkStyleUrl;
    }

    public String getPmdUrl() {
        return pmdUrl;
    }

    public void setPmdUrl(String pmdUrl) {
        this.pmdUrl = pmdUrl;
    }

    public String getCpdUrl() {
        return cpdUrl;
    }

    public void setCpdUrl(String cpdUrl) {
        this.cpdUrl = cpdUrl;
    }

    public String getCyclomaticUrl() {
        return cyclomaticUrl;
    }

    public void setCyclomaticUrl(String cyclomaticUrl) {
        this.cyclomaticUrl = cyclomaticUrl;
    }

    public String getSpotBugsUrl() {
        return spotBugsUrl;
    }

    public void setSpotBugsUrl(String spotBugsUrl) {
        this.spotBugsUrl = spotBugsUrl;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
