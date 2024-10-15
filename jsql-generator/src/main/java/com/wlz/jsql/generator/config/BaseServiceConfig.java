package com.wlz.jsql.generator.config;

public class BaseServiceConfig {
    private String packageName;



    public BaseServiceConfig(String packageName) {
        this.packageName = packageName;

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


}
