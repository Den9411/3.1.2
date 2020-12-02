package ru.nasekin.bootstrap.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nasekin.bootstrap.model.Role;
import ru.nasekin.bootstrap.model.User;
import ru.nasekin.bootstrap.repository.RoleRepository;
import ru.nasekin.bootstrap.service.RoleService;
import ru.nasekin.bootstrap.service.UserService;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String findAll(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("admin", userService.findByUsername(name));
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }


    @PostMapping
    public String createUser(User user, @RequestParam("role") String[] roles){
        Long id = user.getId();
        if (id==null) {
            for (String role : roles) {
                if (role.toLowerCase().contains("admin")) {
//                user.setRoles(Set.of(new Role(2L, "ROLE_ADMIN", Set.of(user))));
                    user.setRoles(Set.of(roleService.findByRole("ROLE_ADMIN")));
                }
                if (role.toLowerCase().contains("user")) {
//                user.setRoles(Set.of(new Role(1L, "ROLE_USER", Set.of(user))));
                    user.setRoles(Set.of(roleService.findByRole("ROLE_USER")));
                }
            }
        } else {
            Set<Role> userRoles = userService.findById(id).getRoles();
            for (String role : roles) {
                if (role.toLowerCase().contains("admin")) {
                    userRoles.add(roleService.findByRole("ROLE_ADMIN"));
                    user.setRoles(userRoles);
                }
                if (role.toLowerCase().contains("user")) {
                    userRoles.add(roleService.findByRole("ROLE_USER"));
                    user.setRoles(userRoles);
                }
            }
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
