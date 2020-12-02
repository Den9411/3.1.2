package ru.nasekin.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nasekin.bootstrap.model.Role;
import ru.nasekin.bootstrap.model.User;
import ru.nasekin.bootstrap.repository.RoleRepository;
import ru.nasekin.bootstrap.repository.UserRepository;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public Role saveUser(Role role){
        return roleRepository.save(role);
    }

    @Transactional
    public Role findByRole (String r) {
        List<Role> roles = findAll();
        for (Role role: roles) {
            if (role.getRole().equals(r)){
                return role;
            }
        }
        return null;
    }
}
