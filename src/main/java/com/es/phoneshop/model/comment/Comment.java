package com.es.phoneshop.model.comment;

public class Comment {

    private String commentText;
    private String authorName;
    private Rate rate;

    public Comment(String commentText, String authorName, Rate rate) {
        this.commentText = commentText;
        this.authorName = authorName;
        this.rate = rate;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
