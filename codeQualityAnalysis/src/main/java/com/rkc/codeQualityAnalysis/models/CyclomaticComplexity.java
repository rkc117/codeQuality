package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Document(collection = "cyclomaticComplexity")
public class CyclomaticComplexity {

    @Id
    private String id;
    //
    private String type;
    private String fileName;
    private List<MethodCyclomatic> methodCyclomatics = new LinkedList<>();
    private String AvgNLOC;
    private String AvgCCN;
    private String AvgToken;
    private String functionCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<MethodCyclomatic> getMethodCyclomatics() {
        return methodCyclomatics;
    }

    public void setMethodCyclomatics(List<MethodCyclomatic> methodCyclomatics) {
        this.methodCyclomatics = methodCyclomatics;
    }

    public String getAvgNLOC() {
        return AvgNLOC;
    }

    public void setAvgNLOC(String avgNLOC) {
        AvgNLOC = avgNLOC;
    }

    public String getAvgCCN() {
        return AvgCCN;
    }

    public void setAvgCCN(String avgCCN) {
        AvgCCN = avgCCN;
    }

    public String getAvgToken() {
        return AvgToken;
    }

    public void setAvgToken(String avgToken) {
        AvgToken = avgToken;
    }

    public String getFunctionCount() {
        return functionCount;
    }

    public void setFunctionCount(String functionCount) {
        this.functionCount = functionCount;
    }
}
