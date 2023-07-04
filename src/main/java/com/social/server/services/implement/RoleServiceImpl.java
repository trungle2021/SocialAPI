package com.example.socialmediaproject.services.implement;


import com.example.socialmediaproject.entities.Roles;
import com.example.socialmediaproject.exceptions.ResourceNotFoundException;
import com.example.socialmediaproject.exceptions.SocialAppException;
import com.example.socialmediaproject.repositories.RoleRepository;
import com.example.socialmediaproject.services.RoleService;
import com.example.socialmediaproject.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.socialmediaproject.utils.SD.ROLE;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Roles> getAll(){
        List<Roles> rolesList = roleRepository.findAll();
        if(rolesList.isEmpty()){
            throw new SocialAppException(HttpStatus.INTERNAL_SERVER_ERROR,"Role Data is empty");
        }
        return rolesList;
    }

    @Override
    public Roles getOneByRoleType(String roleName) {
        Roles role = roleRepository.findByRoleType(roleName).get(0);
        if(role == null){
            throw new ResourceNotFoundException(ROLE,"role_name",roleName);
        }
        return role;
    }

    @Override
    public Roles getOneById(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ROLE,"id",id));
    }

    @Override
    public Roles getOneByAccountId(String accountId) {
        Roles role = roleRepository.findByAccountId(accountId);
        if(role != null){
            return role;
        }
        throw new SocialAppException(HttpStatus.BAD_REQUEST,"User Not Have Any Role");
    }

    @Override
    public Roles create(Roles roleDTO) {
        Roles role = new Roles();
        validateRoles(roleDTO);
        role.setRoleType(roleDTO.getRoleType());
        return roleRepository.save(role);
    }

    @Override
    public Roles update(String id) {
        Roles role = roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ROLE,"id",id));
        Roles roleDTO = EntityMapper.mapToDto(role,Roles.class);
        validateRoles(roleDTO);
        role.setRoleType(roleDTO.getRoleType());
        return roleRepository.save(role);
    }

    @Override
    public void delete(String id) {
        Roles role = roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ROLE,"id",id));
        roleRepository.delete(role);
    }

    private void validateRoles(Roles roleDTO){

        boolean roleNameIsExisted = roleRepository
                .findAll().stream()
                .map(Roles::getRoleType)
                .anyMatch(r->r.equals(roleDTO.getRoleType()));

        List<String> errorList = new ArrayList<>();
        if(roleDTO.getRoleType().isEmpty()){
            errorList.add("Role Name is required");
        }else if(roleNameIsExisted){
            errorList.add("Role Name is existed");
        }

        if(!errorList.isEmpty()){
            throw new SocialAppException(HttpStatus.BAD_REQUEST,errorList.toString());
        }
    }
}
