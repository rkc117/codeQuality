package com.rkc.codeQualityAnalysis.controllers;

import com.rkc.codeQualityAnalysis.services.CyclomaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
@CrossOrigin
public class CyclomaticController {

    @Autowired
    private CyclomaticService cyclomaticService;

    @GetMapping("/codeQuality/cyclomatic/{requestId}")
    public ResponseEntity<?> getCheckStyleDetails(@PathVariable("requestId") String requestId, @RequestParam(required = false,defaultValue = "file",value = "type") String type){
        return ResponseEntity.ok(new HashMap<>(){{
            put("status", 200);
            put("message", "success");
            put("data", cyclomaticService.getCyclomaticComplexity(requestId,type));
        }});
    }

}
