package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator= "users_id_seq")
    @SequenceGenerator(name="users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable=false, length=255, name="firstName")
    private String firstName;

    @Column(nullable=false, length=255, name="surname")
    private String surname;

    @Column(unique=true, nullable = false, length=255, name="email")
    private String email;

    @Column(nullable=false, length=255, name="password")
    private String password;


    //private long imageId;

    @Column(nullable=false, name="locale")
    private Locale locale;

    @OneToOne(mappedBy="user")
    private Seller seller;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();
    //TODO: Should roles be final?

    User(){//Just for hibernate, we love you!
        }
    public User(Long id, String firstName, String surname, String email, String password, Locale locale){
        super();
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.locale = locale;
    }

    public User(String firstName, String surname, String email, String password, Locale locale){
        this(null, firstName, surname, email, password, locale);
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
    public String getPassword() {
        return password;
    }
    public Long getId() {
        return id;
    }

    /*public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }*/

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
