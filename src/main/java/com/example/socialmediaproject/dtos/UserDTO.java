package com.example.socialmediaproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private Integer age;
    private String accountId;
    private Boolean is_deleted;
    private AccountDTO accountsByAccountId;
}
