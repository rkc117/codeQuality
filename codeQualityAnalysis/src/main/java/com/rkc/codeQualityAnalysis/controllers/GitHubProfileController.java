package com.rkc.codeQualityAnalysis.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rkc.codeQualityAnalysis.services.ProfileService;

@Controller
public class GitHubProfileController {
	
	@Autowired
	ProfileService profileService;

	@GetMapping("/profile/github/{userName}/score")
	public ResponseEntity<?> getGitHubProfileInfo(@PathVariable("userName") String userName) throws JsonParseException, JsonMappingException, IOException{
        return ResponseEntity.ok(profileService.getGitHubProfileInfo(userName,null));
    }
	
	@GetMapping("/profile/github/{userName}/{repoName}/score")
	public ResponseEntity<?> getGitHubProfileInfo(@PathVariable("userName") String userName,@PathVariable("repoName") String repoName) throws JsonParseException, JsonMappingException, IOException{
        return ResponseEntity.ok(profileService.getGitHubProfileInfo(userName,repoName));
    }
	
	/*
	 * @GetMapping("/profile/spoj/{userId}") public ResponseEntity<?>
	 * getSpojProfileInfo(@PathVariable("userId") String requestId) throws
	 * JsonParseException, JsonMappingException, IOException{ return
	 * profileService.getGitHubProfileInfo(requestId); }
	 * 
	 * @GetMapping("/profile/codechef/{userId}") public ResponseEntity<?>
	 * getCodechefHubProfileInfo(@PathVariable("userId") String requestId) throws
	 * JsonParseException, JsonMappingException, IOException{ return
	 * profileService.getGitHubProfileInfo(requestId); }
	 * 
	 * @GetMapping("/profile/hackerrank/{userId}") public ResponseEntity<?>
	 * getHackerrankHubProfileInfo(@PathVariable("userId") String requestId) throws
	 * JsonParseException, JsonMappingException, IOException{ return
	 * profileService.getGitHubProfileInfo(requestId); }
	 */
	
}
