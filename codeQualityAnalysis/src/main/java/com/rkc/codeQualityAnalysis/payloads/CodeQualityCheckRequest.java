package com.rkc.codeQualityAnalysis.payloads;

import java.util.ArrayList;
import java.util.List;

public class CodeQualityCheckRequest {
    List<String> gitRepos = new ArrayList<>();

    public List<String> getGitRepos() {
        return gitRepos;
    }

    public void setGitRepos(List<String> gitRepos) {
        this.gitRepos = gitRepos;
    }
}
