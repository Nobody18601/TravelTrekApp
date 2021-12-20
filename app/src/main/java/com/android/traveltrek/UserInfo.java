package com.android.traveltrek;

public class UserInfo {

    private String username;
    private String email;
    private String contact;
    private String country;

    public UserInfo(){

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getCountry() {
        return country;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
