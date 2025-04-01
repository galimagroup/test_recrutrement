package sow.issa.recrutement.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemRequest implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    @NotNull(message = "Choisir un produit")
    private Long productId;

    @NotNull(message = "Préciser la quantité")
    private Integer quantity;
}
