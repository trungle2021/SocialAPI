package com.social.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Date;

import static com.social.server.configs.ElasticSearch.Indices.USER_INDEX;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = USER_INDEX,createIndex = true)
public class UserDTO {
    @Id
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
