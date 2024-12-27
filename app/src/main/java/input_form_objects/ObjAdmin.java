package input_form_objects;

public class ObjAdmin {
    private String email, password, name, contactNumber;

    public ObjAdmin() {}

    public ObjAdmin(String email, String password, String name, String contactNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "ObjAdmin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
