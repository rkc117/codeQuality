package com.rkc.codeQualityAnalysis.models;

public class GitHubProfile {
	private String userName;
	private float ownerId;
	private float id;
	private String repoName;
	private float size;
	private float stargazers_count;
	private float watchers_count;
	private String language;
	private boolean has_issues;
	private boolean has_downloads;
	private boolean has_wiki;
	private boolean has_pages;
	private float forks_count;
	private float open_issues_count;
	public float getId() {
		return id;
	}
	public void setId(float id) {
		this.id = id;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public float getStargazers_count() {
		return stargazers_count;
	}
	public void setStargazers_count(float stargazers_count) {
		this.stargazers_count = stargazers_count;
	}
	public float getWatchers_count() {
		return watchers_count;
	}
	public void setWatchers_count(float watchers_count) {
		this.watchers_count = watchers_count;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isHas_issues() {
		return has_issues;
	}
	public void setHas_issues(boolean has_issues) {
		this.has_issues = has_issues;
	}
	public boolean isHas_downloads() {
		return has_downloads;
	}
	public void setHas_downloads(boolean has_downloads) {
		this.has_downloads = has_downloads;
	}
	public boolean isHas_wiki() {
		return has_wiki;
	}
	public void setHas_wiki(boolean has_wiki) {
		this.has_wiki = has_wiki;
	}
	public boolean isHas_pages() {
		return has_pages;
	}
	public void setHas_pages(boolean has_pages) {
		this.has_pages = has_pages;
	}
	public float getForks_count() {
		return forks_count;
	}
	public void setForks_count(float forks_count) {
		this.forks_count = forks_count;
	}
	public float getOpen_issues_count() {
		return open_issues_count;
	}
	public void setOpen_issues_count(float open_issues_count) {
		this.open_issues_count = open_issues_count;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(float ownerId) {
		this.ownerId = ownerId;
	}
}
