package org.ht.external.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.ht.external.user.contants.WSO2Params;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UserAccountUnlockRequest {
    private List<Object> schemas = Collections.singletonList(WSO2Params.PATCH_UPDATE);

    @JsonProperty(WSO2Params.OPERATIONS)
    private List<PatchUpdateData> operations;
}
