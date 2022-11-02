package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoriteId implements Serializable {

    private static final long serialVersionUID = -3524447512070111317L;
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "seller_id")
    private Long sellerId;

    public FavoriteId(){}

    public FavoriteId(Long userId, Long sellerId) {
        this.userId = userId;
        this.sellerId = sellerId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteId id = (FavoriteId) o;
        return Objects.equals(userId, id.userId) && Objects.equals(sellerId, id.sellerId);
    }
}
