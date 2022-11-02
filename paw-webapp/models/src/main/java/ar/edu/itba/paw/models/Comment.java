package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_id_seq")
    @SequenceGenerator(name="comments_id_seq", sequenceName = "comments_id_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500, nullable = false, name="message")
    private String message;

    @ManyToOne
    @JoinColumn(name="userid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="productid", nullable = false)
    private Product product;

    @Column(name="datetime")
    private LocalDateTime dateTime;

    @Column(length=500, name="reply")
    private String reply;

    public Comment(){}

    public Comment(String message, User user, Product product, LocalDateTime dateTime){
        this(null, message, user, product, dateTime);
    }

    public Comment(Long id, String message, User user, Product product, LocalDateTime dateTime){
        this.id = id;
        this.message=message;
        this.user = user;
        this.product = product;
        this.dateTime = dateTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}

