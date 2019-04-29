package com.example.ayb_itshare;

import java.lang.reflect.Constructor;

public class Model {
    private String post_image, post_description, post_user;


    public Model(String post_image, String post_description, String post_user) {
        this.post_image = post_image;
        this.post_description = post_description;
        this.post_user = post_user;
    }

    public String getPost_image() {
        return post_image;
    }

    public String getPost_description() {
        return post_description;
    }

    public String getPost_user() {
        return post_user;
    }
}
