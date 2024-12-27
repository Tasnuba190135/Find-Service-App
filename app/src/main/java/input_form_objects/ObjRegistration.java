package input_form_objects;

public class ObjRegistration extends ObjectParent1 {
    private String name, email, password, contact, gender, profession, district, division, addressDetails, about;
    private int post_quota, rating, rating_count, approval;

    public ObjRegistration(){}

    public ObjRegistration(String name, String email, String password, String contact, String gender, String profession, String district, String division, String addressDetails, String about) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.gender = gender;
        this.profession = profession;
        this.district = district;
        this.division = division;
        this.addressDetails = addressDetails;
        this.about = about;

        post_quota = 2;
        rating = 0;
        rating_count = 0;
        approval = 0;
    }

    @Override
    public String toString() {
        return "ObjRegistration{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contact='" + contact + '\'' +
                ", gender='" + gender + '\'' +
                ", profession='" + profession + '\'' +
                ", district='" + district + '\'' +
                ", division='" + division + '\'' +
                ", addressDetails='" + addressDetails + '\'' +
                ", about='" + about + '\'' +
                ", post_quota=" + post_quota +
                ", rating=" + rating +
                ", rating_count=" + rating_count +
                ", approval=" + approval +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getPost_quota() {
        return post_quota;
    }

    public void setPost_quota(int post_quota) {
        this.post_quota = post_quota;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }
}
