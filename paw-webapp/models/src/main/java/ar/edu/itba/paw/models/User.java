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

    @Column(nullable=false, name="locale")
    private Locale locale;

    @OneToOne(mappedBy="user")
    private Seller seller;

    @Column(name="notifications")
    private Boolean notifications;

    @OneToOne
    @JoinColumn(name = "image_id", nullable = true)
    private Image image;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

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
        this.notifications = false;
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

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public void toggleNotifications(){
        this.notifications = !this.notifications;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    public void deleteImage(){this.image = null;}
}
