package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sellers_id_seq")
    @SequenceGenerator(name="sellers_id_seq", sequenceName = "sellers_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name="userid")
    private User user;

    @Column(nullable=false, length = 20)
    private String phone;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "areaid", nullable = true)
    //@Enumerated(EnumType.ORDINAL)
    //TODO: This should work except for AGRONOMÍA, check for solutions
    //  Update: Changed default value of areaid to -1, and changed agronomia ID to 0 (now matches ordinal)
    private Long areaId;


    @OneToMany(mappedBy = "seller")
    private Set<Product> products;

    @OneToMany(mappedBy = "seller")
    private Set<Article> news;

    Seller(){}

    public Seller(User user, String phone, String address, long areaId){
        this(null, user, phone, address, areaId);
    }


    public Seller(Long id, User user, String phone, String address, long areaId) {
        this.id = id;
        this.user = user;
        this.phone = phone;
        this.address = address;
        this.areaId = areaId;
    }

    public Long getId() {
        return id;
    }

    public User getUser() { return user; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(getId(), seller.getId());
    }
}
