package ru.nasekin.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error !=null);
        model.addAttribute("logout", logout !=null);
        return "login";
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Start!");
        messages.add("I'm CRUD application with Spring Security + Bootstrap");
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
