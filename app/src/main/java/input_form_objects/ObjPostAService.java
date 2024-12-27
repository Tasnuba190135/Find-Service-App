package input_form_objects;

public class ObjPostAService extends ObjectParent1 {
    private String name, profession, email, description, address, division, district;
    private int approve;

    public ObjPostAService(){}

    public ObjPostAService(String name, String profession, String email, String description, String address, String division, String district) {
        this.name = name;
        this.profession = profession;
        this.email = email;
        this.description = description;
        this.address = address;
        this.division = division;
        this.district = district;

        approve = 0;
    }

    @Override
    public String toString() {
        return "ObjPostAService{" +
                "name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", division='" + division + '\'' +
                ", district='" + district + '\'' +
                ", approve=" + approve +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }
}
