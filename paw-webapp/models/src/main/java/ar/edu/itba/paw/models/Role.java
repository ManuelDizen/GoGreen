package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
    @SequenceGenerator(name="roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    Role(){}

    public Role(String name){
        this(null, name);
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
