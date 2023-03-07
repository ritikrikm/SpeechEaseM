package com.example.speechease.modelVH;

public class template_md {
    String name,uid,key,key2;
    public template_md(){

    }
    public template_md(String name,String uid,String key,String key2){
        this.name = name;
        this.key = key;
        this.uid = uid;
        this.key2 = key2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }
}
