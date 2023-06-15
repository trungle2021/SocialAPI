package com.example.socialmediaproject.dtos;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
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
