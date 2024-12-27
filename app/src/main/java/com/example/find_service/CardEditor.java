package com.example.find_service;


//Model class
public class CardEditor {
    private String name, address, button, email, details;
    private String request_sender_email, service_post_id;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRequest_sender_email() {
        return request_sender_email;
    }

    public void setRequest_sender_email(String request_sender_email) {
        this.request_sender_email = request_sender_email;
    }

    public String getService_post_id() {
        return service_post_id;
    }

    public void setService_post_id(String service_post_id) {
        this.service_post_id = service_post_id;
    }
}
