package ar.edu.itba.paw.models;

public class Seller {
    private long id;
    private String name;
    private String mail;
    private String phone;
    private String address;

    public Seller(long id, String mail, String phone, String address, String name) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

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
