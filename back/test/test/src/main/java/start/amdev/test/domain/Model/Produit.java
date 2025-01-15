package start.amdev.test.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import start.amdev.test.domain.enums.InventoryStatus;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "TP_produit")
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Prod_id", nullable = false)
    private Long id;
    @Column(unique = true, nullable = false, name = "Prod_code")
    private String code;
    @Column(nullable = true, name = "Prod_name")
    private String name;
    @Column(nullable = true, name = "Prod_description")
    private String description;
    @Column(nullable = true, name = "Prod_image")
    private String image;
    @Column(nullable = true, name = "Prod_price")
    private long price;
    @Column(nullable = true, name = "Prod_category")
    private String category;
    @Column(nullable = true, name = "Prod_quantity")
    private Long quantity;
    @Column(nullable = true, name = "Prod_internal_reference")
    private String internalReference;
    @Column(nullable = true, name = "Prod_shell_id")
    private String shellId;
    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;
    @Column(nullable = true, name = "Prod_rating")
    private int rating;
//    @Column(nullable = false, name = "Prod_created_at")
//    private Date createdAt;
//    @Column(nullable = false, name = "Prod_updated_at")
//    private Date updatedAt;
}
