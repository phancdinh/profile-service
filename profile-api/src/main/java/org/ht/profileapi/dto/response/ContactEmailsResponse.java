package org.ht.profileapi.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.ht.profileapi.dto.response.internal.HierarchyContactResponse;
import java.util.List;

@Setter
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactEmailsResponse {
    private List<HierarchyContactResponse> emails;
}
