package ar.edu.itba.paw.models;

public class User {

    private long id;
    private String email;
    private String username;
    private String password;

    public User(long id, String email, String username, String password){
        super();
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() { return username; }
    public String getPassword() {
        return password;
    }
    public long getId() {
        return id;
    }
}
