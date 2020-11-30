package org.ht.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.ht.profile.dto.internal.BaseIdentityDocumentDto;

import java.util.ArrayList;

@Getter
@Setter
public class LegalInfoDto extends BaseDto {
    private ObjectId id;
    private ObjectId profileId;
    private BaseIdentityDocumentDto nationalIdentity;
    private BaseIdentityDocumentDto citizenIdentity;
    private ArrayList<BaseIdentityDocumentDto> passports;
}
