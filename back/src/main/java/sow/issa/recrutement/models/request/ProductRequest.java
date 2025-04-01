package sow.issa.recrutement.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sow.issa.recrutement.entities.enums.InventoryStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    @NotBlank(message = "Le code est obligatoire")
    private String code;

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    private String description;

    private String image;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.01", message = "Le prix doit être supérieur à 0")
    private Double price;


    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantity;

    @NotBlank(message = "La reference est obligatoire")
    private String internalReference;

    @NotNull(message = "Le status est obligatoire")
    private InventoryStatus inventoryStatus;

    private Integer rating;

    @NotBlank(message = "La catégorie est obligatoire")
    private String category;

    @NotNull(message = "Précisé l'étagère")
    private Integer shelfId;
}
