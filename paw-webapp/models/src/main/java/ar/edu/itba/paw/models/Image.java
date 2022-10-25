package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_id_seq")
    @SequenceGenerator(name="images_id_seq", sequenceName = "images_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private byte[] source;

    @OneToMany(mappedBy="image")
    private Set<Product> products;

    @OneToMany(mappedBy="news")
    private Set<Article> articles;

    Image(){}

    public Image(byte[] source){
        this(null, source);
    }

    public Image(Long id, byte[] source) {
        this.id = id;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }
}
