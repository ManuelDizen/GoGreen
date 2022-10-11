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
    @JoinColumn(name="users_id", nullable = false)
    private long userId;

    @ManyToOne(optional = false)
    @JoinColumn(name="roles_id", nullable = false)
    private long roleId;

    UserRole(){}

    public UserRole(long userId, long roleId){
        this(null, userId, roleId);
    }

    public UserRole(Long id, long userId, long roleId){
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoleId() {
        return roleId;
    }
}
