package sow.issa.recrutement.entities;

import jakarta.persistence.*;
import lombok.*;
import sow.issa.recrutement.entities.audits.Auditable;

import java.io.Serializable;

@Table(name = "carts")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "cart_linked_user", nullable = false, unique = true)
    private UserEntity user;

    @Column(name = "cart_linked_total_price")
    private Double totalPrice;
}
