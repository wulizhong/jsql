package com.wlz.jsql.generator.config;

import java.util.ArrayList;
import java.util.List;

public class TConfig {
    private String packageName;

    private List<String> prefixList = new ArrayList<>();

    private List<String> tables;

    public TConfig(String packageName, List<String> prefixList, List<String> tables) {
        this.packageName = packageName;
        this.prefixList = prefixList;
        this.tables = tables;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getPrefixList() {
        return prefixList;
    }

    public void setPrefixList(List<String> prefixList) {
        this.prefixList = prefixList;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}
