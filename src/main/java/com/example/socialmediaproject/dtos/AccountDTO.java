package com.example.socialmediaproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    public String accountId;
    public String email;
    public Boolean isDeleted;
    public String oauth;
    public String password;
    public String roleId;
    public RoleDTO rolesByRoleId;
    public Collection<UserDTO> usersByAccountId;
}
