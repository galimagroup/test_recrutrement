package sow.issa.recrutement.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ProductResponse implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    private Long id;

    private String code;

    private String name;

    private String description;

    private String image;

    private Double price;

    private Integer quantity;

    private String internalReference;

    private InventoryStatus inventoryStatus;

    private Integer rating;

    private String category;

    private Integer shelfId;
}
