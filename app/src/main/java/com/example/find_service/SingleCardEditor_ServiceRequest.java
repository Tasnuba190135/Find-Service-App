package com.example.find_service;

public class SingleCardEditor_ServiceRequest {
    private String ratingID, servicedescription;
    private String servicePostID, serviceReceiverEmail, serviceGiverEmail;
    private int acceptByServiceGiver;

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }

    public String getServicedescription() {
        return servicedescription;
    }

    public void setServicedescription(String servicedescription) {
        this.servicedescription = servicedescription;
    }

    public String getServicePostID() {
        return this.servicePostID;
    }

    public void setServicePostID(String servicePostID) {
        this.servicePostID = servicePostID;
    }

    public String getServiceReceiverEmail() {
        return serviceReceiverEmail;
    }

    public void setServiceReceiverEmail(String serviceReceiverEmail) {
        this.serviceReceiverEmail = serviceReceiverEmail;
    }

    public String getServiceGiverEmail() {
        return serviceGiverEmail;
    }

    public void setServiceGiverEmail(String serviceGiverEmail) {
        this.serviceGiverEmail = serviceGiverEmail;
    }

    public int getAcceptByServiceGiver() {
        return acceptByServiceGiver;
    }

    public void setAcceptByServiceGiver(int acceptByServiceGiver) {
        this.acceptByServiceGiver = acceptByServiceGiver;
    }
}
