package com.rkc.codeQualityAnalysis.models;

public class DuplicateFile {

    private String lineNumber;
    private String fileName;
    private String totalLines;

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(String totalLines) {
        this.totalLines = totalLines;
    }
}
