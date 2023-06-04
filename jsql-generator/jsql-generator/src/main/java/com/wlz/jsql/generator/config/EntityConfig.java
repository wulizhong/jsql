package com.wlz.jsql.generator.config;


public class EntityConfig {
    private String packageName;


    public EntityConfig(String packageName) {
        this.packageName = packageName;

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
