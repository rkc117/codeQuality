package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "checkstyles")
public class CheckStyle {

    @Id
    private String id;

    private String userName;

    private String repository;

    private String fileName;

    private String rowNumber;

    private String colNumber;

    private String level;

    private String checkstylesMessage;

    private String checkStyleCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getCheckstylesMessage() {
        return checkstylesMessage;
    }

    public void setCheckstylesMessage(String checkstylesMessage) {
        this.checkstylesMessage = checkstylesMessage;
    }

    public String getCheckStyleCategory() {
        return checkStyleCategory;
    }

    public void setCheckStyleCategory(String checkStyleCategory) {
        this.checkStyleCategory = checkStyleCategory;
    }
}
