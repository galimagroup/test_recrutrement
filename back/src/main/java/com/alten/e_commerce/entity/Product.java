package com.alten.e_commerce.entity;

import java.time.Instant;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.alten.e_commerce.enums.InventoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue
    private Long id;

    private String code;
    
    private String name;

    private String description;

    private String image;

    private String category;

    private Integer price;

    private Integer quantity;

    private String intervalReference;

    private Integer shellId;

    private Integer rating;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = true, updatable = false)
    private Instant createdAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = true)
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<ShoppingCart> carts;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedProducts")
    Set<User> likes;

}
