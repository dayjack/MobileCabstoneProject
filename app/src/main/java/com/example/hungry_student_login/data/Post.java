package com.example.hungry_student_login.data;

public class Post {
    private String title;

    private String contents;

    // 임시
    private String postedDate;

    private String username;

    private Integer likeCount;

    private Integer commentCount;

    public Post(String title, String contents, String postedDate, String username, Integer commentCount) {
        this.title = title;
        this.contents = contents;
        this.postedDate = postedDate;
        this.username = username;

        this.commentCount = commentCount;
    }

    public String getTitle() {
        return title;
    }


    public String getContents() {
        return contents;
    }


    public String getPostedDate() {
        return postedDate;
    }


    public String getUsername() {
        return username;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

}
