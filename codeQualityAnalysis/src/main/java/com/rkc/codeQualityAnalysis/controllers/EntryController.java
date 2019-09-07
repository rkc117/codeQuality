package com.rkc.codeQualityAnalysis.controllers;


import com.rkc.codeQualityAnalysis.payloads.CodeQualityCheckRequest;
import com.rkc.codeQualityAnalysis.services.CodeQualityCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin
public class EntryController {

    @Autowired
    private CodeQualityCheckerService codeQualityCheckerService;

    @PostMapping("/codeQuality/check")
    public ResponseEntity<?> check(@RequestBody CodeQualityCheckRequest codeQualityCheckRequest) {

        return codeQualityCheckerService.check(codeQualityCheckRequest);
    }

    @GetMapping("/codeQuality/result/{requestId}")
    public ResponseEntity<?> getResults(@PathVariable("requestId")  String requestId){
        return codeQualityCheckerService.getResult(requestId);
    }

}
