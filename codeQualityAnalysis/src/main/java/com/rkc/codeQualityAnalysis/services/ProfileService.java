package com.rkc.codeQualityAnalysis.services;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rkc.codeQualityAnalysis.models.GitHubProfile;
import com.rkc.codeQualityAnalysis.repositories.GitHubProfileRepositry;

@Service
public class ProfileService {
	
	
	RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	GitHubProfileRepositry repositry;

	public float getGitHubProfileInfo(String userName, String repoName) {
		List<GitHubProfile> profiles;
		if(repoName!=null) {
			profiles = repositry.findAllByUserNameAndRepoName(userName, repoName);
		}else {
			profiles = repositry.findAllByUserName(userName);
		}
		
		if(profiles.isEmpty()) {
			final String uri = "https://api.github.com/users/"+userName+"/repos?visibility=public&fork=false";
		     
			ResponseEntity entity = restTemplate.getForEntity(uri, String.class);
			JSONArray repos = new JSONArray(entity.getBody().toString());
			for(Object object : repos) {
				JSONObject obj = new JSONObject(object.toString());
				GitHubProfile profile = getMappedHubProfile(obj,userName);
				profiles.add(profile);
				repositry.save(profile);
			}
		}
		
		return calculateScore(profiles);
	}

	private float calculateScore(List<GitHubProfile> profiles) {
		float score = 0;
		for(GitHubProfile profile : profiles) {
			score+= getScoreBasedOnField(profile.getForks_count(),"FORK_COUNT");
			score+= getScoreBasedOnField(profile.getStargazers_count(),"STARGAZERS_COUNT");
			score+= getScoreBasedOnField(profile.getWatchers_count(),"WATCHERS_COUNT");
			score+= getScoreBasedOnField(profile.isHas_issues(),"HAS_ISSUES");
			score+= getScoreBasedOnField(profile.isHas_downloads(),"HAS_DOWNLOADS");
			score+= getScoreBasedOnField(profile.isHas_wiki(),"HAS_WIKI");
			score+= getScoreBasedOnField(profile.isHas_pages(),"HAS_PAGES");
			score-= getScoreBasedOnField(profile.getOpen_issues_count(),"OPEN_ISSUES_COUNT");
			
			score = score/8;
		}
		
		return score;
	}

	private float getScoreBasedOnField(Object object, String fieldName) {
		switch(fieldName) {
		case "STARGAZERS_COUNT" :
		case "FORK_COUNT" 	: 
		case "WATCHERS_COUNT" :	float f_count = (float)object;
								return (f_count)/(9999);
		case "HAS_ISSUES" : return (boolean)object?0l:1l;
		case "HAS_DOWNLOADS" : 
		case "HAS_WIKI" 	: 
		case "HAS_PAGES" 	: return (boolean)object?1l:0l;
		case "OPEN_ISSUES_COUNT" : float count = (float)object;
								   return (count)/(1000);
		default : return 0l;
		}
	}

	private GitHubProfile getMappedHubProfile(JSONObject obj, String userName) { 
		GitHubProfile profile = new GitHubProfile();
		
		profile.setUserName(userName);
		//profile.setOwnerId(obj.getJSONObject("owner").getFloat("id"));
		profile.setId(obj.getFloat("id"));
		profile.setRepoName(obj.getString("name"));
		profile.setSize(obj.getFloat("size"));
		profile.setStargazers_count(obj.getFloat("stargazers_count"));
		profile.setWatchers_count(obj.getFloat("watchers_count"));
		//profile.setLanguage(obj.getString("language"));
		profile.setHas_issues(obj.getBoolean("has_issues"));
		profile.setHas_downloads(obj.getBoolean("has_downloads"));
		profile.setHas_wiki(obj.getBoolean("has_wiki"));
		profile.setForks_count(obj.getFloat("forks_count"));
		profile.setHas_pages(obj.getBoolean("has_pages"));
		profile.setOpen_issues_count(obj.getFloat("open_issues_count"));
		
		return profile;
	}
}
