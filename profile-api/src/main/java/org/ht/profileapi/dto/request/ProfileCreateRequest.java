package org.ht.profileapi.dto.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProfileCreateRequest {

    private String leadSource;
    private BasicInfoCreateRequest basicInfo;
    @Valid
    @NotNull(message = "{validation.contact.notEmpty}")
    private ContactInfoCreateRequest contactInfo;
    private LegalInfoCreateRequest legalInfo;

}
