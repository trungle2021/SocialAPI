package com.social.server.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private Date dob;
    private String address;
    private String origin;
    private String accountId;
    private Boolean isDeleted;

}
