package input_form_objects;

public class ObjContactUs extends ObjectParent1 {
    private String email, contactNo, issueType, description;
    private String feedback;
    private int isSolved;
    public ObjContactUs() {
    }

    public ObjContactUs(String email, String contactNo, String issueType, String description) {
        this.email = email;
        this.contactNo = contactNo;
        this.issueType = issueType;
        this.description = description;

        feedback = "";
        isSolved = 0;
    }

    @Override
    public String toString() {
        return "ObjContactUs{" +
                "email='" + email + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", issueType='" + issueType + '\'' +
                ", description='" + description + '\'' +
                ", feedback='" + feedback + '\'' +
                ", isSolved=" + isSolved +
                '}';
    }

//    public String getStringMinimal() {
//        String s = "email='" + email + "\n" +
//                ", contactNo='" + contactNo + "\n" +
//                ", issueType='" + issueType + "\n" +
//                ", description='" + description + "\n" +
//                ", feedback='" + feedback + "\n" +
//                ", isSolved=" + isSolved + "\n";
//        return s;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getIsSolved() {
        return isSolved;
    }

    public void setIsSolved(int isSolved) {
        this.isSolved = isSolved;
    }
}
