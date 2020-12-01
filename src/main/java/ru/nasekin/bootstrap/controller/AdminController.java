package ru.nasekin.bootstrap.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nasekin.bootstrap.model.Role;
import ru.nasekin.bootstrap.model.User;
import ru.nasekin.bootstrap.service.UserService;

import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
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
        for(String role: roles) {
            if(role.toLowerCase().contains("admin")){
                user.setRoles(Set.of(new Role(2L, "ROLE_ADMIN", Set.of(user))));
            }
            if(role.toLowerCase().contains("user")){
                user.setRoles(Set.of(new Role(1L, "ROLE_USER", Set.of(user))));
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
