package org.ht.id.profileapi.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ActivationCreateRequest {

    @NotBlank(message = "{validation.account.email.required}")
    @Email(message = "{validation.account.email.isNotValid}")
    private String email;

    @NotBlank(message = "{validation.activation.phone.required}")
    private String phone;

    private String htId;


}
