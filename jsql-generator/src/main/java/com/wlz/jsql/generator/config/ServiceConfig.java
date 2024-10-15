package com.wlz.jsql.generator.config;

public class ServiceConfig {
    private String packageName;

    public ServiceConfig(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
