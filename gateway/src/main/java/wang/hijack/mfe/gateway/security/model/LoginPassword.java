package wang.hijack.mfe.gateway.security.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Jack
 */
@Data
public class LoginPassword {
    @NotBlank
    private String oldPass;
    @NotBlank
    private String newPass;
}
