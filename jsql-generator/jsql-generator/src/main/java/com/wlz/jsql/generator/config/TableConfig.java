package com.wlz.jsql.generator.config;

import com.wlz.jsql.generator.Table;

public class TableConfig {
    private String packageName;

    private Table table;

    private String prefix;

    private String tableName;



    public TableConfig(String packageName, String tableName, String prefix) {
        this.packageName = packageName;
        this.tableName = tableName;
        this.prefix = prefix;

    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
