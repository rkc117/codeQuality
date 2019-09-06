package com.rkc.codeQualityAnalysis.controllers;

import com.rkc.codeQualityAnalysis.services.CheckStylesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CheckStylesController {

    @Autowired
    private CheckStylesService checkStylesService;

    @GetMapping("/codeQuality/checkStyles/{requestId}")
    public ResponseEntity<?> getCheckStyleDetails(@PathVariable("requestId") String requestId){
        return checkStylesService.getCheckStyles(requestId);
    }
 }
