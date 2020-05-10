package com.example.hp_awareness_app;

public class Article {
    private String ID, link;
    private Object timeStamp;
    private boolean visible;

    public Article() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }



    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

