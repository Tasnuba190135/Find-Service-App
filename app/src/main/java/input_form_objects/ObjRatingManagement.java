package input_form_objects;

public class ObjRatingManagement {
    private String servicePostID, serviceDescription, serviceReceiverEmail, serviceGiverEmail;
    private int acceptByServiceGiver;

    public ObjRatingManagement() {
    }

    public ObjRatingManagement(String servicePostID, String serviceDescription, String serviceReceiverEmail, String serviceGiverEmail, int acceptByServiceGiver) {
        this.servicePostID = servicePostID;
        this.serviceDescription = serviceDescription;
        this.serviceReceiverEmail = serviceReceiverEmail;
        this.serviceGiverEmail = serviceGiverEmail;
        this.acceptByServiceGiver = acceptByServiceGiver;
    }

    public String getServicePostID() {
        return servicePostID;
    }

    public void setServicePostID(String servicePostID) {
        this.servicePostID = servicePostID;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
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
