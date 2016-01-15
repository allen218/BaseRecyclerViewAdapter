package com.example.activity.recyclerviewloadmore.bean;

import java.io.Serializable;

/**
 * Created by allen on 1/14/2016 17:18.
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String emailId;

    public Student() {

    }
    public Student(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }


}
