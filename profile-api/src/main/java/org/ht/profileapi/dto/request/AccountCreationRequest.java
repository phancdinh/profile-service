package org.ht.profileapi.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.common.constant.ValidationPattern;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountCreationRequest {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = ValidationPattern.PASSWORD_PATTERN, message = "Password does not meet requirement")
    private String password;

    private String htId;

    private String leadSource;
}
