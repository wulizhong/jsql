package com.wlz.jsql.generator.config;

public class DataBaseConfig {
    private String url;
    private String account;

    private String password;

    public DataBaseConfig(String url, String account, String password) {
        this.url = url;
        this.account = account;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
