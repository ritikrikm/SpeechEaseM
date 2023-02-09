package com.example.speechease.Utils;

public class firebasemodel {

    String name;

    String uid;
        String status;
        String key;


    public firebasemodel(String key,String name, String uid,String status) {
        this.name = name;
this.key = key;
        this.uid = uid;
        this.status = status;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public firebasemodel() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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



}
