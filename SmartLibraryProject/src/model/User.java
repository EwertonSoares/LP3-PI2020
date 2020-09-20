/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ewerton
 */
public class User {

    private Long codUser;
    private String userName;
    private String userType;
    private String phone;
    private String mobilePhone;
    private String email;

    public User() {
    }

    public User(Long codUser, String userName, String userType, String phone, String mobilePhone, String email) {
        this.codUser = codUser;
        this.userName = userName;
        this.userType = userType;
        this.phone = phone;
        this.mobilePhone = mobilePhone;
        this.email = email;
    }

    public Long getCodUser() {
        return codUser;
    }

    public void setCodUser(Long codUser) {
        this.codUser = codUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phones) {
        this.phone = phones;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
