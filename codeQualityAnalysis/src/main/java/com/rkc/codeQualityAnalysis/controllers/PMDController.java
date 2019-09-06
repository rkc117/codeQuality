package com.rkc.codeQualityAnalysis.controllers;

import com.rkc.codeQualityAnalysis.services.PMDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PMDController {

    @Autowired
    private PMDService pmdService;

    @GetMapping("/codeQuality/pmd/{requestId}")
    public ResponseEntity<?> getCheckStyleDetails(@PathVariable("requestId") String requestId){
        return pmdService.getPMD(requestId);
    }

}
