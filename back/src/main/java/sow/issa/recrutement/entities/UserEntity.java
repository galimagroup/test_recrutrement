package sow.issa.recrutement.entities;

import jakarta.persistence.*;
import lombok.*;
import sow.issa.recrutement.entities.audits.Auditable;

import java.io.Serializable;
import java.util.Objects;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usr_id")
    private Long id;

    @Column(name = "usr_login", unique = true)
    private String username;

    @Column(name = "usr_email", unique = true)
    private String email;

    @Column(name = "usr_password")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CartEntity cart;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WishlistEntity wishlist;

    public void assignCartAndWishlist() {
        if (Objects.nonNull(this.cart)) {
            this.cart.setUser(this);
        }
        if (Objects.nonNull(this.wishlist)) {
            this.wishlist.setUser(this);
        }
    }
}
