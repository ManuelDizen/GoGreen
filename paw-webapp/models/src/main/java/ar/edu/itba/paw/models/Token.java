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

    @Column(name = "used", nullable = false)
    private Boolean used;

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
        this.used = false;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpDate() {
        return expDate;
    }

    public boolean expired() {
        return getExpDate().isAfter(LocalDateTime.now());
    }

    public Boolean getUsed() {
        return this.used;
    }
    public void setUsed(Boolean used) {
        this.used = used;
    }

    public boolean isUsed(){ return this.used; }
    public void use(){ this.used = true;}
    public boolean isValid(){return !this.expired() && !this.isUsed();}
}
