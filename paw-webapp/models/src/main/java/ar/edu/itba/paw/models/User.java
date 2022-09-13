package ar.edu.itba.paw.models;

public class User {

    private long id;
    private String firstName;
    private String surname;
    private String email;
    private String username;
    private String password;

    public User(long id, String firstName, String surname, String email, String username, String password){
        super();
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
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
