package com.example.acer.moviemonsters;

public class JsonReview {
    String content;
    String author;
    String link;
    String id;

    public JsonReview(String author, String content, String link, String id) {
        this.author = author;
        this.content = content;
        this.link = link;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getLink() {
        return link;

    }

}
