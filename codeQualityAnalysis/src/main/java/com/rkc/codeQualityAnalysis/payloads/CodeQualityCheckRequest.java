package com.rkc.codeQualityAnalysis.payloads;

import java.util.ArrayList;
import java.util.List;

public class CodeQualityCheckRequest {
    List<KeyValue> keyValues = new ArrayList<>();

    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }
}
