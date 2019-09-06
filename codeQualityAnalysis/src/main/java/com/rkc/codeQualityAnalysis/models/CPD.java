package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "cpd")
public class CPD {

    @Id
    private String id;
    private String requestId;
    private String userName;
    private String repository;
    private String code;
    private Integer tokenLength;
    private List<DuplicateFile> duplicateFiles = new ArrayList<>();


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

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repostory) {
        this.repository = repostory;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTokenLength() {
        return tokenLength;
    }

    public void setTokenLength(Integer tokenLength) {
        this.tokenLength = tokenLength;
    }

    public List<DuplicateFile> getDuplicateFiles() {
        return duplicateFiles;
    }

    public void setDuplicateFiles(List<DuplicateFile> duplicateFiles) {
        this.duplicateFiles = duplicateFiles;
    }
}
