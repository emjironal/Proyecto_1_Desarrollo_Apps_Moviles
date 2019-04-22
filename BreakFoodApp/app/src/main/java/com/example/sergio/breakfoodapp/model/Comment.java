package com.example.sergio.breakfoodapp.model;

import java.util.Date;

public class Comment {

    private String content;
    private Date publishDate;
    private String owner;


    public Comment() {
    }

    public Comment(String content, Date publishDate, String owner) {
        this.content = content;
        this.publishDate = publishDate;
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getOwner() {
        return owner;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
