package org.ht.externalUser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

import static org.ht.externalUser.contants.WSO2Params.OPERATIONS;
import static org.ht.externalUser.contants.WSO2Params.PATCH_UPDATE;

@Getter
@Setter
public class UserAccountUnlockRequest {
    private List<Object> schemas = Collections.singletonList(PATCH_UPDATE);

    @JsonProperty(OPERATIONS)
    private List<PatchUpdateData> operations;
}
