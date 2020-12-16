package org.ht.external.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.ht.external.user.contants.WSO2Params.EXTENSIONS;

@Getter
@Setter
public class UserCreationRequest {
    private List<Object> schemas = new ArrayList<>();
    private List<String> emails = new ArrayList<>();
    private String userName;
    private String password;

    @JsonProperty(EXTENSIONS)
    private Map<String, Object> extensions;
}

