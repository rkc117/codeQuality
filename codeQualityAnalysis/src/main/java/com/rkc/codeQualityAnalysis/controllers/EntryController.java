package com.rkc.codeQualityAnalysis.controllers;


import com.rkc.codeQualityAnalysis.payloads.CodeQualityCheckRequest;
import com.rkc.codeQualityAnalysis.services.CodeQualityCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class EntryController {

    @Autowired
    private CodeQualityCheckerService codeQualityCheckerService;

    @GetMapping("/check")
    public ResponseEntity<?> check(@RequestBody CodeQualityCheckRequest codeQualityCheckRequest) {

        return codeQualityCheckerService.check(codeQualityCheckRequest);
    }

}
