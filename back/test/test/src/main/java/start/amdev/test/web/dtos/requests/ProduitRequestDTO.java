package start.amdev.test.web.dtos.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import start.amdev.test.domain.enums.InventoryStatus;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProduitRequestDTO{
    private String name;
    private String code;
    private String description;
    String image;
    private String price;
    private String category;
    private Long quantity;
    private String internalReference;
    private String shellId;
    private Long rating;
    private InventoryStatus inventoryStatus;
//    private Date createdAt;
//    private Date updatedAt;
}
