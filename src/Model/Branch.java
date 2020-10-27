package Model;


import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable {


    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String webAddress;
    private String username;
    private String password;
    private ArrayList<Property> properties;

    public Branch() {

    }

    //Constructor using a Branch object
    public Branch(Branch branch) {
        name = branch.getName();
        address = branch.getAddress();
        phoneNumber = branch.getPhoneNumber();
        emailAddress = branch.getEmailAddress();
        webAddress = branch.getWebAddress();
        username = branch.getUsername();
        password = branch.getPassword();
        this.properties = new ArrayList<Property>();
        this.properties = branch.getProperties();
    }

    //Constructor using attributes.
    public Branch(String n, String a, String p, String e, String w, String u, String pass) {
        name = n;
        address = a;
        phoneNumber = p;
        emailAddress = e;
        webAddress = w;
        username = u;
        password = pass;
        this.properties = new ArrayList<Property>();
    }

    //Sets the attributes to the values of the parameter. Does not create a new object.
    public void updateBranch(Branch branch) {
        name = branch.getName();
        address = branch.getAddress();
        phoneNumber = branch.getPhoneNumber();
        emailAddress = branch.getEmailAddress();
        webAddress = branch.getWebAddress();
        username = branch.getUsername();
        password = branch.getPassword();
        this.properties = branch.getProperties();
    }

    //Setters
    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public void setProperties(ArrayList<Property> properties) {
        this.properties = new ArrayList<>();

        this.properties = properties;
    }

    //Getters
    public String getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }


    public void deleteProperty(Property p) {
        this.properties.remove(p);

    }

    public void addProperty(Property p) {
        this.properties.add(p);
    }


    //Used during development.
    public void printProperties() {

        for (int index = 0; index < properties.size(); index++) {
            System.out.println("Index: " + index + " Name: " + properties.get(index).getAddress());
        }
    }

}
