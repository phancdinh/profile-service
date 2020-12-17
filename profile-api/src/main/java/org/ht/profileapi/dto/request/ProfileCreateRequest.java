package org.ht.profileapi.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.ht.profileapi.validator.constraint.ContactInfoRequired;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileCreateRequest {
    @NotEmpty(message = "{validation.leadSource.notEmpty}")
    private String leadSource;
    private BasicInfoCreateRequest basicInfo;
    @Valid
    @NotNull(message = "{validation.contact.notEmpty}")
    @ContactInfoRequired(message = "{validation.contact.atLeast.primary}")
    private ContactInfoCreateRequest contactInfo;
    private LegalInfoCreateRequest legalInfo;

}
