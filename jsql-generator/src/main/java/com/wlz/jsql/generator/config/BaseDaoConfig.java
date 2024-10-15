package com.wlz.jsql.generator.config;

import com.wlz.jsql.generator.Table;

public class BaseDaoConfig {
    private String packageName;



    public BaseDaoConfig(String packageName) {
        this.packageName = packageName;

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }


}
