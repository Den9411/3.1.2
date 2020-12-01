package ru.nasekin.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nasekin.bootstrap.service.UserService;


import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    private UserService userService;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Start!");
        messages.add("I'm CRUD application with Spring Security");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping("/user")
    public String show( Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userService.findByUsername(name));
        return "user";
    }

}
