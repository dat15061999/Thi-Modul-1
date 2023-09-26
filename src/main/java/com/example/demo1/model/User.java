package com.example.demo1.model;

import com.example.demo1.model.enumration.EGender;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Date dob;
    private List<UserRole> userRole;
    private EGender gender;

    public User() {
    }

    public User(int id, String firstname, String lastname, String username, String email, Date dob, List<UserRole> role, EGender gender) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.dob = dob;
        this.userRole = role;
        this.gender = gender;
    }

    public User(String firstname, String lastname, String username, String email, Date bod, List<UserRole> role, EGender gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.dob = bod;
        this.userRole = role;
        this.gender = gender;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setRole(List<UserRole> role) {
        this.userRole = role;
    }
    public String getRoles(){
        //[3,2] -> "3, 2"
        return userRole.stream()
                .map(e -> e.getRole().getName()).collect(Collectors.joining(", "));
    }
}
