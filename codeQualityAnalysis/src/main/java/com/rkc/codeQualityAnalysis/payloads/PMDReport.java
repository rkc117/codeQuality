package com.rkc.codeQualityAnalysis.payloads;

import java.util.ArrayList;
import java.util.List;

public class PMDReport {

    private String requestId;
    private String totalWarnings;
    private String averageWarnings;
    private String totalFiles;
    private List<PMDFileReport> pmdFileReports = new ArrayList<>();

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTotalWarnings() {
        return totalWarnings;
    }

    public void setTotalWarnings(String totalWarnings) {
        this.totalWarnings = totalWarnings;
    }

    public String getAverageWarnings() {
        return averageWarnings;
    }

    public void setAverageWarnings(String averageWarnings) {
        this.averageWarnings = averageWarnings;
    }

    public String getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(String totalFiles) {
        this.totalFiles = totalFiles;
    }

    public List<PMDFileReport> getPmdFileReports() {
        return pmdFileReports;
    }

    public void setPmdFileReports(List<PMDFileReport> pmdFileReports) {
        this.pmdFileReports = pmdFileReports;
    }
}
