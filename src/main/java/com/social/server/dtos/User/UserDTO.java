package com.social.server.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private Date dob;
    private String address;
    private String origin;
}
