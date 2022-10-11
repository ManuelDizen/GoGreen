package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name="sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="sellers_id_seq")
    @SequenceGenerator(name="sellers_id_seq", sequenceName = "sellers_id_seq", allocationSize = 1)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="users_id")
    private long userid;

    @Column(nullable=false, length = 20)
    private String phone;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(name = "areaid")
    @Enumerated(EnumType.ORDINAL)
    //TODO: This should work except for AGRONOMÍA, check for solutions
    //  Update: Changed default value of areaid to -1, and changed agronomia ID to 0 (now matches ordinal)
    private long areaId;

    Seller(){}

    public Seller(long userId, String phone, String address, long areaId){
        this(null, userId, phone, address, areaId);
    }


    public Seller(Long id, long userid, String phone, String address, long areaId) {
        this.id = id;
        this.userid = userid;
        this.phone = phone;
        this.address = address;
        this.areaId = areaId;
    }

    public Long getId() {
        return id;
    }

    public long getUserId() { return userid; }

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

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }
}
