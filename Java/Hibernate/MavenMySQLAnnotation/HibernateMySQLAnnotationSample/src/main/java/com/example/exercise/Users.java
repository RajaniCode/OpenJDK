package com.example.exercise;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

// import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @Column(name="username")
    String username;
 
    @Column(name="login_date")
    java.sql.Date loginDate;

    @Column(name="login_time")
    java.sql.Time loginTime;

    @Column(name="created_at")
    java.sql.Timestamp createdAt;

    @Column(name="updated_at")
    java.sql.Timestamp updatedAt;

    public Users () { }
 
    public Users(int id, String username, java.sql.Date loginDate, java.sql.Time loginTime, java.sql.Timestamp createdAt, java.sql.Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
        this.createdAt = createdAt;
	this.updatedAt = updatedAt;
    }
 
    public int getId() {
        return id;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public java.sql.Date getLoginDate() {
        return loginDate;
    }
 
    public void setLoginDate(java.sql.Date loginDate) {
        this.loginDate = loginDate;
    }

    public java.sql.Time getLoginTime() {
        return loginTime;
    }
 
    public void setLoginTime(java.sql.Time loginTime) {
        this.loginTime = loginTime;
    }
   
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public java.sql.Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(java.sql.Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}