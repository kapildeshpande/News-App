package com.example.kapil.newsapp;

/**
 * Created by kapil on 04-03-2018.
 */

public class JSONData {
    private String title;
    private String description;
    private String imageURL;

    public JSONData(String title, String description, String imageURL) {
        this.title = title;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }
}
