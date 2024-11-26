package com.example.newsapp;

public class DataArticle {
    private String title, link, image_url, source_id;


    public DataArticle(String title, String link, String imageURL, String source_id) {
        this.title = title;
        this.link = link;
        this.image_url = imageURL;
        this.source_id = source_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageURL() {
        return image_url;
    }

    public void setImageURL(String imageURL) {
        this.image_url = imageURL;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSourse_id(String sourse_id) {
        this.source_id = sourse_id;
    }
}
