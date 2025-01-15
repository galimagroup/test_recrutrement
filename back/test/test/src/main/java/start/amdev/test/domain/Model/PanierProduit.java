package start.amdev.test.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TR_produit_panier")
@NoArgsConstructor
@AllArgsConstructor
public class PanierProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PanierProduit_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id", nullable = false)
    private Panier panier; // Relation ManyToOne avec Panier

    @ManyToOne
    @JoinColumn(name = "prod_id", nullable = false)
    private Produit produit; // Relation ManyToOne avec Produit

    @Column(name = "quantite", nullable = false)
    private int quantite; // Quantité du produit dans le panier

//    @Column(name = "prix", nullable = false)
//    private long prix; // Prix du produit

}
