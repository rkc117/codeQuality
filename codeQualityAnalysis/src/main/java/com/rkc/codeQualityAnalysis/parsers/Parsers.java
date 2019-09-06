package com.rkc.codeQualityAnalysis.parsers;

import com.rkc.codeQualityAnalysis.models.CPD;
import com.rkc.codeQualityAnalysis.models.CheckStyle;
import com.rkc.codeQualityAnalysis.models.DuplicateFile;
import com.rkc.codeQualityAnalysis.models.PMD;
import com.rkc.codeQualityAnalysis.repositories.CPDRepository;
import com.rkc.codeQualityAnalysis.repositories.CheckStylesRepository;
import com.rkc.codeQualityAnalysis.repositories.PMDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class Parsers {

    @Autowired
    private CheckStylesRepository checkStylesRepository;
    @Autowired
    private PMDRepository pmdRepository;
    @Autowired
    private CPDRepository cpdRepository;

    public void parseAndSave(InputStream inputStream, String type, String userName) {

        if ("pmd".equalsIgnoreCase(type)) {

            List<PMD> pmds = new ArrayList<>();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = br.readLine()) != null) {

                    String[] split = line.split(":\t");

                    if (split.length < 2) {
                        continue;
                    }

                    PMD pmd = new PMD();

                    String[] split1 = split[0].split(":");
                    if (split1.length == 2) {
                        pmd.setFileName(split1[0]);
                        pmd.setLineNumber(split1[1]);
                    }
                    if (split1.length == 1) ;
                    {
                        pmd.setFileName(split1[0]);
                    }
                    if (split1.length > 2) {
                        pmd.setFileName(split1[0]);
                    }

                    pmd.setMessage(split[1]);

                    pmd.setUserName(userName);

                    pmds.add(pmd);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            pmdRepository.saveAll(pmds);
        }

        if ("checkstyles".equalsIgnoreCase(type)) {

            List<CheckStyle> checkStyles = new ArrayList<>();

            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = br.readLine()) != null) {

                    String[] split = line.split(": ");

                    System.out.println(Arrays.toString(split));

                    if (split.length < 3) {
                        continue;
                    }
                    CheckStyle checkStyle = new CheckStyle();

                    checkStyle.setUserName(userName);
                    setErrorLevelAndFile(checkStyle, split[0]);
                    checkStyle.setCheckStyleCategory(split[1]);
                    checkStyle.setCheckstylesMessage(split[2]);
                    setCategory(checkStyle, split[2]);
                    checkStyles.add(checkStyle);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            checkStylesRepository.saveAll(checkStyles);
        }
    }

    private void setCategory(CheckStyle checkStyle, String s) {
        String[] s1 = s.split(" ");
        if (s1.length > 1) {
            checkStyle.setCheckStyleCategory(s1[s1.length - 1]);
        }
    }

    private void setErrorLevelAndFile(CheckStyle checkStyle, String s) {
        String[] s1 = s.split(" ");
        checkStyle.setLevel(s1[0]);
        String[] split = s1[1].split(":");
        if (split.length == 3) {
            checkStyle.setFileName(split[0]);
            checkStyle.setRowNumber(split[1]);
            checkStyle.setColNumber(split[2]);
        }

        if (split.length == 2) {
            checkStyle.setFileName(split[0]);
            checkStyle.setRowNumber(split[1]);
        }

        if (split.length == 1) {
            checkStyle.setFileName(split[0]);
        }
    }

    public void parseCPD(InputStream inputStream, String userName) {

        List<CPD> cpds = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            LinkedList<String> lines = new LinkedList<>();

            while ((line = br.readLine()) != null) {

                if (line.startsWith("Found a")) {

                    CPD cpd = new CPD();
                    cpd.setUserName(userName);
                    //set Token length
                    //line.split(" ")

                    StringBuilder codeBuilder= new StringBuilder();

                    while ((line = br.readLine()) != null && !line.startsWith("=====================================================================")) {

                        if(line.startsWith("Starting at line")){
                            String[] splits = line.split(" ");

                            if(splits.length>5){
                                DuplicateFile duplicateFile = new DuplicateFile();
                                duplicateFile.setFileName(splits[5]);
                                duplicateFile.setLineNumber(splits[3]);
                                cpd.getDuplicateFiles().add(duplicateFile);

                            }
                            continue;
                        }

                        codeBuilder.append(line).append("\n");
                    }

                    cpd.setCode(codeBuilder.toString());
                    cpds.add(cpd);
                }

                cpdRepository.saveAll(cpds);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
