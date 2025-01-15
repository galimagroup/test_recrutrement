package start.amdev.test.web.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import start.amdev.test.web.dtos.requests.PanierProduitRequestDTO;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class PanierProduitResponseDTO extends PanierProduitRequestDTO {
    //private Long id;
    private String code;     // Code du produit
    private String name;     // Nom du produit
    private long price;      // Prix du produit
    private int quantite;
}
