package org.ht.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BitlyShortenLinkCreationRequest {
    private String group_guid;
    private String domain;
    private String long_url;
    
}

