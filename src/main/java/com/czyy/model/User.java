package com.czyy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 都航本地 on 2017/2/18.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String phone;
    private String number  ;
    private String classes  ;
    private String college ;



    public User(){

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Integer getId() {

        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getNumber() {
        return number;
    }

    public String getClasses() {
        return classes;
    }

    public String getCollege() {
        return college;
    }
}
