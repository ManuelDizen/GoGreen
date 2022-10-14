package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name="user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_id_seq")
    @SequenceGenerator(name="user_roles_id_seq", sequenceName = "user_roles_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name="role_id", nullable = false)
    private Role role;

    UserRole(){}

    public UserRole(User user, Role role){
        this(null, user, role);
    }

    public UserRole(Long id, User user, Role role){
        this.id = id;
        this.user = user;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }
}
