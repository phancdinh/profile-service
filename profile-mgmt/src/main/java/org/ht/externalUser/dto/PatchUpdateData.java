package org.ht.externalUser.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PatchUpdateData {
    private String op;
    private Map<String, Object> value;
}
