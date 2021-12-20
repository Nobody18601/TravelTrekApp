package com.android.traveltrek;

public class PostUploadInfo {
    public String title;
    public String desc;
    public String location;
    public String imageURL;

    public PostUploadInfo() {

    }

    public PostUploadInfo(String title, String desc, String location, String imageURL) {
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
