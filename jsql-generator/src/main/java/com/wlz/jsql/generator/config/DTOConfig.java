package com.wlz.jsql.generator.config;


public class DTOConfig {
    private String packageName;


    public DTOConfig(String packageName) {
        this.packageName = packageName;

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
