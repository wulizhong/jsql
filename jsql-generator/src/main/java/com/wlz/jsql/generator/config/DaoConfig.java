package com.wlz.jsql.generator.config;

public class DaoConfig {
    private String packageName;

    public DaoConfig(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
