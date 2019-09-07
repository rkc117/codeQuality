package com.rkc.codeQualityAnalysis.payloads;

import java.util.ArrayList;
import java.util.List;

public class CPDReport {

    private String requestId;
    private String totalWarnings;
    private String averageWarnings;
    private String totalFiles;
    List<CPDFileReport> cpdFileReports =  new ArrayList<>();

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

    public List<CPDFileReport> getCpdFileReports() {
        return cpdFileReports;
    }

    public void setCpdFileReports(List<CPDFileReport> cpdFileReports) {
        this.cpdFileReports = cpdFileReports;
    }
}
