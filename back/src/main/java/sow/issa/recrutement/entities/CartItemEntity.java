package sow.issa.recrutement.entities;

import jakarta.persistence.*;
import lombok.*;
import sow.issa.recrutement.entities.audits.Auditable;

import java.io.Serializable;

@Table(name = "cart_items")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cait_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cait_linked_cart", nullable = false)
    private CartEntity cart;

    @ManyToOne
    @JoinColumn(name = "cait_linked_product", nullable = false)
    private ProductEntity product;

    @Column(name = "cait_quantity")
    private Integer quantity;

    @Column(name = "cait_price")
    private Double price;
}
