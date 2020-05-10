package com.example.hp_awareness_app;

public class NewsContent {
    private int image;
    private String title;
    private String desc;
    public NewsContent(int image,String title,String desc){
        this.image = image;
        this.title = title;
        this.desc = desc;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
}
