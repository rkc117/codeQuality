package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
public class Files {

    @Id
    private String id;
    private String name;
    private String totalNumberLines;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalNumberLines() {
        return totalNumberLines;
    }

    public void setTotalNumberLines(String totalNumberLines) {
        this.totalNumberLines = totalNumberLines;
    }
}
