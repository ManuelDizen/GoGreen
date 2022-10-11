package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_seq")
    @SequenceGenerator(name = "orders_id_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String buyerSurname;
    @Column(nullable = false)
    private String buyerEmail;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false)
    private String sellerSurname;

    @Column(nullable = false)
    private String sellerEmail;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(name="message")
    private String message;
    private String parsedDateTime;

    Order(){}

    public Order(String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, Integer price, LocalDateTime dateTime, String message){
        this(null,productName, buyerName, buyerSurname, buyerEmail, sellerName, sellerSurname,
                sellerEmail, amount, price, dateTime, message);
    }

    public Order(Long id, String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, Integer price, LocalDateTime dateTime, String message) {
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
        this.dateTime = dateTime;
        this.message = message;
        this.parsedDateTime = this.getParsedDateTime();
    }

    public Long getId() {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public String getParsedDateTime() {
        return getDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"));
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
