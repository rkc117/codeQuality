package com.rkc.codeQualityAnalysis.controllers;

import com.rkc.codeQualityAnalysis.services.SpotBugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@CrossOrigin
public class SpotBugsController {

    @Autowired
    private SpotBugService spotBugService;

    @GetMapping("/codeQuality/spotBugs/{requestId}")
    public ResponseEntity<?> getCheckStyleDetails(@PathVariable("requestId") String requestId) {
        return null;
    }
}
