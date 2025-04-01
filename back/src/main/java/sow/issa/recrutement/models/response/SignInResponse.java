package sow.issa.recrutement.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sow.issa.recrutement.entities.enums.TokenType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInResponse implements Serializable {
    private static final long serialVersionUID = -5387827484974552092L;

    private String token;

    private TokenType tokenType = TokenType.BEARER;
}
