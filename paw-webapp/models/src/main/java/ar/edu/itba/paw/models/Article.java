package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="news")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "news_id_seq")
    @SequenceGenerator(name="news_id_seq", sequenceName = "news_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name="imageid", nullable = false)
    private Image image;

    @Column(length = 1023, nullable = false, name="message")
    private String message;

    @ManyToOne
    @JoinColumn(name="sellerid", nullable = false)
    private Seller seller;

    @Column(name="datetime")
    private LocalDateTime dateTime;

    public Article(){}

    public Article(Image image, String message, Seller seller, LocalDateTime dateTime){
        this(null, image, message, seller, dateTime);
    }

    public Article(Long id, Image image, String message, Seller seller, LocalDateTime dateTime){
        this.id = id;
        this.image = image;
        this.message=message;
        this.seller=seller;
        this.dateTime = dateTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getParsedDateTime() {
        return getDateTime().format(DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy"));
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
