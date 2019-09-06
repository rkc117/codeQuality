package com.rkc.codeQualityAnalysis.models;

public class MethodCyclomatic {

    private String name;
    private String startLine;
    private String endLine;
    private String tokenLength;
    private String param;
    private String CCN;
    private String NLOC;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public String getEndLine() {
        return endLine;
    }

    public void setEndLine(String endLine) {
        this.endLine = endLine;
    }

    public String getTokenLength() {
        return tokenLength;
    }

    public void setTokenLength(String tokenLength) {
        this.tokenLength = tokenLength;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCCN() {
        return CCN;
    }

    public void setCCN(String CCN) {
        this.CCN = CCN;
    }

    public String getNLOC() {
        return NLOC;
    }

    public void setNLOC(String NLOC) {
        this.NLOC = NLOC;
    }
}
