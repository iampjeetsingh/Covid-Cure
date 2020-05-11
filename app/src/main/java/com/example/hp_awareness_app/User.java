package com.example.hp_awareness_app;

public class User {
    private String Name;
    private String DateTime;
    private String Contact;
    private String Address;
    private String Message;
    private String Id;

    public User(){}

    public User(String name, String dateTime, String contact, String address, String message, String id) {
       this.Name = name;
        this.DateTime = dateTime;
        this.Contact = contact;
        this.Address = address;
        this.Message = message;
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

