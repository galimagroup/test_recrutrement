package sow.issa.recrutement.entities;

import jakarta.persistence.*;
import lombok.*;
import sow.issa.recrutement.entities.audits.Auditable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name = "wishlists")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishlistEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wish_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "wish_linked_user", nullable = false, unique = true)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> products = new HashSet<>();
}
