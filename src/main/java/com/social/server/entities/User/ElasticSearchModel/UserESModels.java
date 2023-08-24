package com.social.server.entities.User.ElasticSearchModel;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Date;

import static com.social.server.configs.ElasticSearch.Indices.USER_INDEX;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(indexName = USER_INDEX)
public class UserESModels {
    private String id;
    @Field(type = FieldType.Text)
    private String username;
    @Field(type = FieldType.Text)
    private String firstName;
    @Field(type = FieldType.Text)
    private String lastName;
    @Field(type = FieldType.Text)
    private String phone;
    private String gender;
    @Field(type = FieldType.Date)
    private Date dob;
    private String address;
    @Field(type = FieldType.Text)
    private String origin;
}
