package com.rkc.codeQualityAnalysis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
@CrossOrigin
public class FilesController {

    @GetMapping("/codeQuality/files")
    public ResponseEntity<?> getFileContentByFilePath(@RequestParam("path") String path) {

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ResponseEntity.ok(contentBuilder.toString());
    }
}
