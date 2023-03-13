package com.example.speechease.modelVH;

public class internall_md {

    public String name;
    public String uid;
    public String key;
    public internall_md() {
    }

    public internall_md(String name,String uid,String key){
        this.name = name;
        this.key = key;
        this.uid = uid;
        }

    public String getUid() {
        return uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
