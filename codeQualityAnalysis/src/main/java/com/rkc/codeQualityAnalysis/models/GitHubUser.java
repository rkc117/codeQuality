package com.rkc.codeQualityAnalysis.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "gitHubUser")
public class GitHubUser {

    private String userName;


}
