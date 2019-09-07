package com.rkc.codeQualityAnalysis.parsers;

import com.rkc.codeQualityAnalysis.models.*;
import com.rkc.codeQualityAnalysis.repositories.CPDRepository;
import com.rkc.codeQualityAnalysis.repositories.CheckStylesRepository;
import com.rkc.codeQualityAnalysis.repositories.CyclomaticComplexityRepository;
import com.rkc.codeQualityAnalysis.repositories.PMDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Parsers {

    @Autowired
    private CheckStylesRepository checkStylesRepository;
    @Autowired
    private PMDRepository pmdRepository;
    @Autowired
    private CPDRepository cpdRepository;
    @Autowired
    private CyclomaticComplexityRepository cyclomaticComplexityRepository;

    public void parseAndSave(InputStream inputStream, String type, String userName,String requestId,List<Files> files) {

        if ("pmd".equalsIgnoreCase(type)) {

            Map<String, Files> filePathToFilesMap = files.stream().parallel().collect(Collectors.toMap(Files::getId, Function.identity()));

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
                    pmd.setRequestId(requestId);
                    pmd.setUserName(userName);
                    pmd.setTotalLines(String.valueOf(filePathToFilesMap.get(split1[0]).getTotalNumberLines()));
                    pmds.add(pmd);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            pmdRepository.saveAll(pmds);
        }

        if ("checkstyles".equalsIgnoreCase(type)) {

            List<CheckStyle> checkStyles = new ArrayList<>();

            Map<String, Files> filePathToFilesMap = files.stream().parallel().collect(Collectors.toMap(Files::getId, Function.identity()));

            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = br.readLine()) != null) {

                    String[] split = line.split(": ");

                    if (split.length < 3) {
                        continue;
                    }
                    CheckStyle checkStyle = new CheckStyle();

                    checkStyle.setUserName(userName);
                    checkStyle.setRequestId(requestId);
                    setErrorLevelAndFile(checkStyle, split[0]);
                    checkStyle.setCheckStyleCategory(split[1]);
                    checkStyle.setCheckstylesMessage(split[2]);
                    setCategory(checkStyle, split[2]);
                    checkStyle.setTotalLines(String.valueOf(filePathToFilesMap.get(checkStyle.getFileName()).getTotalNumberLines()));
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

    public void parseCPD(InputStream inputStream, String userName,String requestId) {

        List<CPD> cpds = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            LinkedList<String> lines = new LinkedList<>();

            while ((line = br.readLine()) != null) {

                if (line.startsWith("Found a")) {

                    CPD cpd = new CPD();
                    cpd.setUserName(userName);
                    cpd.setRequestId(requestId);
                    //set Token length
                    //line.split(" ")

                    StringBuilder codeBuilder = new StringBuilder();

                    while ((line = br.readLine()) != null && !line.startsWith("=====================================================================")) {

                        if (line.startsWith("Starting at line")) {
                            String[] splits = line.split(" ");

                            if (splits.length > 5) {
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

    public void parseCyclomatic(InputStream inputStream, String userName,String requestId) {

        List<CyclomaticComplexity> cyclomaticComplexities = new LinkedList<>();
        HashMap<String,CyclomaticComplexity> filePathToCyclomaticComplexity = new HashMap<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            LinkedList<String> lines = new LinkedList<>();


            while ((line = br.readLine()) != null) {

                //populate method level data
                while (((line = br.readLine()) != null) && !"==============================================================".equalsIgnoreCase(line)) {

                    if((line = br.readLine())!= null && "------------------------------------------------".equalsIgnoreCase(line)){

                        while ((line = br.readLine())!= null && !line.endsWith("analyzed.")) {

                            if(line.endsWith(".java")){
                                String[] splits = line.split(" ");
                                int i=0;
                                while("".equalsIgnoreCase(splits[i++]));
                                String value1 = splits[i-1];
                                while("".equalsIgnoreCase(splits[i++]));
                                String value2 = splits[i-1];
                                while("".equalsIgnoreCase(splits[i++]));
                                String value3 = splits[i-1];
                                while("".equalsIgnoreCase(splits[i++]));
                                String value4 = splits[i-1];
                                if(i<=splits.length&&"".equalsIgnoreCase(splits[i])){
                                    while("".equalsIgnoreCase(splits[i++]));
                                }
                                String value5 = splits[i-1];
                                boolean isExecutedInsideLoop =false;
                                if(i<=splits.length && "".equalsIgnoreCase(splits[i])){
                                    while(i<= splits.length&& "".equalsIgnoreCase(splits[i++]));
                                    isExecutedInsideLoop=true;

                                }

                                String value6 =null;

                                if(isExecutedInsideLoop){
                                     value6= splits[i-1];
                                }else{
                                     value6 = splits[i];
                                }

                                String[] split = value6.split("::");

                                String[] split1 = split[1].split("@");

                                CyclomaticComplexity cyclomaticComplexity = new CyclomaticComplexity();
                                cyclomaticComplexity.setRequestId(requestId);
                                cyclomaticComplexity.setFileName(split1[0]);
                                filePathToCyclomaticComplexity.putIfAbsent(split1[2],cyclomaticComplexity);

                                cyclomaticComplexity.setType("method");

                                cyclomaticComplexity = filePathToCyclomaticComplexity.get(split1[2]);

                                MethodCyclomatic methodCyclomatic = new MethodCyclomatic();
                                methodCyclomatic.setNLOC(value1);
                                methodCyclomatic.setCCN(value2);
                                methodCyclomatic.setTokenLength(value3);
                                methodCyclomatic.setParam(value4);
                                methodCyclomatic.setTokenLength(value5);
                                methodCyclomatic.setLocation(value6);

                                cyclomaticComplexity.getMethodCyclomatics().add(methodCyclomatic);

                            }
                        }


                    }
                }


                //second file level starts here
                while (((line = br.readLine()) != null) && !"=========================================================================================".equalsIgnoreCase(line)) {

                    if((line = br.readLine())!= null && "--------------------------------------------------------------".equalsIgnoreCase(line)){
                        System.out.println("File level starts here");

                        while ((line = br.readLine())!= null && !line.endsWith("=========================================================================================")) {
                            if(line.endsWith(".java")){

                                String[] splits = line.split(" ");
                                int i=0;
                                while("".equalsIgnoreCase(splits[i++]));
                                String value1 = splits[i-1];
                                while("".equalsIgnoreCase(splits[i++]));
                                String value2 = splits[i-1];
                                while("".equalsIgnoreCase(splits[i++]));
                                String value3 = splits[i-1];
                                while("".equalsIgnoreCase(splits[i++]));
                                String value4 = splits[i-1];
                                if(i<=splits.length&&"".equalsIgnoreCase(splits[i])){
                                    while("".equalsIgnoreCase(splits[i++]));
                                }
                                String value5 = splits[i-1];
                                boolean isExecutedInsideLoop =false;
                                if(i<=splits.length && "".equalsIgnoreCase(splits[i])){
                                    while(i<= splits.length&& "".equalsIgnoreCase(splits[i++]));
                                    isExecutedInsideLoop=true;

                                }

                                String value6 =null;

                                if(isExecutedInsideLoop){
                                    value6= splits[i-1];
                                }else{
                                    value6 = splits[i];
                                }


                                CyclomaticComplexity cyclomaticComplexity = new CyclomaticComplexity();
                                cyclomaticComplexity.setFileName(value6);

                                cyclomaticComplexity.setType("file");
                                cyclomaticComplexity.setNLOC(value1);
                                cyclomaticComplexity.setAvgNLOC(value2);
                                cyclomaticComplexity.setAvgCCN(value3);
                                cyclomaticComplexity.setAvgToken(value4);
                                cyclomaticComplexity.setFunctionCount(value5);
                                cyclomaticComplexities.add(cyclomaticComplexity);
                            }
                        }

                    }
                }

            }

            filePathToCyclomaticComplexity.entrySet().forEach(entrySet -> {
                CyclomaticComplexity cyclomaticComplexity = entrySet.getValue();
                cyclomaticComplexities.add(cyclomaticComplexity);
            });
            cyclomaticComplexityRepository.saveAll(cyclomaticComplexities);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
