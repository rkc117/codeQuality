package com.rkc.codeQualityAnalysis.controllers;

import com.rkc.codeQualityAnalysis.services.PMDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

@Controller
@CrossOrigin
public class PMDController {

    @Autowired
    private PMDService pmdService;

    @GetMapping("/codeQuality/pmd/{requestId}")
    public ResponseEntity<?> getCheckStyleDetails(@PathVariable("requestId") String requestId){
        return ResponseEntity.ok(new HashMap<>() {{
            put("data", pmdService.getPMD(requestId,null));
            put("message", "success");
            put("status", 200);
        }});
    }

}
