package org.ht.id.account.dto;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BitlyShortenLinkCreationResponse {
    private Date created_at;
    private String id;
    private String link;
    private String[] custom_bitlinks;
    private String long_url;
    private boolean archived;
    private String[] tags;
    private String[] deeplinks;
    private Map<String, String> references;
}

