package com.social.server.entities.User.ElasticSearchModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserESModels implements Serializable {
    @JsonProperty
    private String id;
    @JsonProperty
    private String username;
    @JsonProperty
    private String documentId;
}
