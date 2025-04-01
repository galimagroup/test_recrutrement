package sow.issa.recrutement.entities;

import jakarta.persistence.*;
import lombok.*;
import sow.issa.recrutement.entities.audits.Auditable;
import sow.issa.recrutement.entities.enums.InventoryStatus;

import java.io.Serializable;

@Table(name = "products")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity extends Auditable<Long> implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "prod_code", nullable = false, unique = true)
    private String code;

    @Column(name = "prod_name", nullable = false)
    private String name;

    @Column(name = "prod_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "prod_image")
    private String image;

    @Column(name = "prod_price")
    private Double price;

    @Column(name = "prod_quantity")
    private Integer quantity;

    @Column(name = "prod_internalReference")
    private String internalReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "prod_inventoryStatus")
    private InventoryStatus inventoryStatus;

    @Column(name = "prod_rating")
    private Integer rating;

    @Column(name = "prod_category")
    private String category;

    @Column(name = "prod_shelfId")
    private Integer shelfId;
}
