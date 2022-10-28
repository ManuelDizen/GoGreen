package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token {

    public static final int EXP_TIME = 60 * 10;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_id_seq")
    @SequenceGenerator(allocationSize = 1, sequenceName = "token_id_seq", name = "token_id_seq")
    private Long id;

    @Column(nullable = false)
    private String passToken;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "exp_date", nullable = false)
    private LocalDateTime expDate;

    Token() {
    }

    public Token(String passToken, User user, LocalDateTime expDate) {
        this(null, passToken, user, expDate);
    }

    public Token(Long id, String passToken, User user, LocalDateTime expDate) {
        this.id = id;
        this.passToken = passToken;
        this.user = user;
        this.expDate = expDate.plusMinutes(EXP_TIME);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassToken() {
        return passToken;
    }

    public void setPassToken(String passToken) {
        this.passToken = passToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDateTime expDate) {
        this.expDate = expDate;
    }
}
