package ar.edu.itba.paw.models;

public class Order {
    private long id;
    private String productName;
    private String buyerName;
    private String buyerSurname;
    private String buyerEmail;
    private String sellerName;
    private String sellerSurname;
    private String sellerEmail;
    private Integer amount;
    private float price;

    public Order(long id, String productName, String buyerName, String buyerSurname, String buyerEmail, String sellerName, String sellerSurname, String sellerEmail, Integer amount, float price) {
        this.id = id;
        this.productName = productName;
        this.buyerName = buyerName;
        this.buyerSurname = buyerSurname;
        this.buyerEmail = buyerEmail;
        this.sellerName = sellerName;
        this.sellerSurname = sellerSurname;
        this.sellerEmail = sellerEmail;
        this.amount = amount;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerSurname() {
        return buyerSurname;
    }

    public void setBuyerSurname(String buyerSurname) {
        this.buyerSurname = buyerSurname;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerSurname() {
        return sellerSurname;
    }

    public void setSellerSurname(String sellerSurname) {
        this.sellerSurname = sellerSurname;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
