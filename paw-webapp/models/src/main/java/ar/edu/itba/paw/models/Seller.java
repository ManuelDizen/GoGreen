package ar.edu.itba.paw.models;

public class Seller {
    private long id;
    private long userid;
    private String phone;
    private String address;

    public Seller(long id, long userid, String phone, String address) {
        this.id = id;
        this.userid = userid;
        this.phone = phone;
        this.address = address;
    }

    public long getId() {
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
}
