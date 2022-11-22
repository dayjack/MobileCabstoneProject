package com.example.hungry_student_login.data;

import java.util.Date;

//@Data

public class PostRequest {

    private String title;
    private String contents;
    private Date postedDate;
    private String username;

    public PostRequest(String title, String contents, String username) {
        this.title = title;
        this.contents = contents;
        this.postedDate = new Date();
        this.username = username;
    }

    public PostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
