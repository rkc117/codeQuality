package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gitHubUserCodeQuality")
public class GitHubUserCodeQuality {

    private String id;
    private String requestId;
    private String userName;
    private String gitHubRepoUrl;
    private String totalLines;
    private String totalNumberFiles;
    private String averageLines;
    private String averageMaintainability;
    private String maintainabilityUrl;
    private String filesUrl;
    private String checkStyleUrl;
    private String pmdUrl;
    private String cpdUrl;
    private String cyclomaticUrl;
    private String spotBugsUrl;
    private Object pmds;
    private Object cpds;
    private Object checkstyles;
    private Object cyclomatics;
    private Float score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getTotalNumberFiles() {
        return totalNumberFiles;
    }

    public void setTotalNumberFiles(String totalNumberFiles) {
        this.totalNumberFiles = totalNumberFiles;
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

    public Object getPmds() {
        return pmds;
    }

    public void setPmds(Object pmds) {
        this.pmds = pmds;
    }

    public Object getCpds() {
        return cpds;
    }

    public void setCpds(Object cpds) {
        this.cpds = cpds;
    }

    public Object getCheckstyles() {
        return checkstyles;
    }

    public void setCheckstyles(Object checkstyles) {
        this.checkstyles = checkstyles;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Object getCyclomatics() {
        return cyclomatics;
    }

    public void setCyclomatics(Object cyclomatics) {
        this.cyclomatics = cyclomatics;
    }
}
