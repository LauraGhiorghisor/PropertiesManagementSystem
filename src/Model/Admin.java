package Model;


import java.io.Serializable;

public class Admin implements Serializable {
    private String username;
    private String password;

    public Admin() {
    }

    public Admin(String u, String p) {
        this.username = u;
        this.password = p;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public void setPassword(String p) {
        this.password = p;
    }

}