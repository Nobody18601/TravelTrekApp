package com.android.traveltrek;

public class MyBlog {
    String title = "";
    String location = "";
    String desc = "";
    String imageURL = "";

    public MyBlog(){

    }

    public MyBlog(String title, String location, String desc, String imageURL) {
        this.title = title;
        this.location = location;
        this.desc = desc;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
