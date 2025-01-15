package start.amdev.test.domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "TP_panier")
@NoArgsConstructor
@AllArgsConstructor
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Panier_id", nullable = false)
    private Long id;

    // Si vous avez une relation avec Utilisateur, décommentez la ligne suivante
    // @ManyToOne
    // @JoinColumn(name = "user_id", nullable = false)
    // private Utilisateur utilisateur;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PanierProduit> produits;

    @Column(name = "total", nullable = false)
    private long total;

    // Méthode pour calculer le total du panier
    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        this.total = produits.stream()
                .mapToLong(p -> p.getProduit().getPrice() * p.getQuantite()) // Calcule le total en multipliant le prix par la quantité
                .sum();
    }

}
