package com.thincbackend.nugu;

import java.util.Map;

public class Result {
    private final String version = "2.0";
    private final String resultCode = "OK";
    private Map<String,String> output;

    public void setOutput(Map<String, String> output) {
        this.output = output;
    }
}
