package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="favorites")
public class Favorite {
    @PrePersist
    private void prePersist(){
        if(this.id == null){
            this.id = new FavoriteId(this.user.getId(), this.seller.getId());
        }
    }

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne(optional = false)
    @MapsId("user_id")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(optional = false)
    @MapsId("seller_id")
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    public Favorite() {
    }

    public Favorite(User user, Seller seller) {
        this.seller = seller;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(getUser(), favorite.getUser()) && Objects.equals(getSeller(), favorite.getSeller());
    }
}
