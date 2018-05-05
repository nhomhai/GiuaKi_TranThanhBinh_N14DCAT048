package com.example.mpxv2.giuaky;

public class Account {
    private String name;
    private String pass;
    private String key;
    public Account(){}
    public Account (String name, String pass, String key)
    {
        this.name=name;
        this.key=key;
        this.pass=pass;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
