package com.rkc.codeQualityAnalysis.payloads;

public class CheckStyleFileReport {
    private String filePath;
    private String rowNumber;
    private String colNumber;
    private String level;
    private String checkStyleMessage;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getColNumber() {
        return colNumber;
    }

    public void setColNumber(String colNumber) {
        this.colNumber = colNumber;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCheckStyleMessage() {
        return checkStyleMessage;
    }

    public void setCheckStyleMessage(String checkStyleMessage) {
        this.checkStyleMessage = checkStyleMessage;
    }
}
