package com.studying.diploma.controller;


import com.studying.diploma.model.Role;
import com.studying.diploma.model.User;
import com.studying.diploma.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String allUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all_users";
    }

    @GetMapping("{user}")
    public String adminEditPage(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "admin_edit";
    }

    @PostMapping("{user}")
    public String userEditRoles(@PathVariable User user,
                                @RequestParam Map<String, String> form) {
        userService.userEditRoles(user, form);
        return "redirect:/users";
    }
}

