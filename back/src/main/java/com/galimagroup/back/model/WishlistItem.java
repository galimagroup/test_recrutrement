package com.galimagroup.back.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class WishlistItem {
    public WishlistItem() {}
    public WishlistItem(Users user, Product product) {
        this.user = user;
        this.product = product;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
