package org.ht.id.profileapi.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ht.id.common.constant.ValidationPattern;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountCreationRequest {
    @NotBlank(message = "{validation.account.email.required}")
    @Email(message = "{validation.account.email.isNotValid}")
    private String email;

    @NotBlank(message = "{validation.account.password.required}")
    @Pattern(regexp = ValidationPattern.PASSWORD_PATTERN, message = "{validation.account.password.isNotMatched}")
    private String password;

    private String htId;

    private String leadSource;
}
