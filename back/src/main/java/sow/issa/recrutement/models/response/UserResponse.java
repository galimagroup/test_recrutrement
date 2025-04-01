package sow.issa.recrutement.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    private Long id;

    private String username;

    private String email;

    private Double cartTotalPrice;

    private Integer wishlistSize;
}
