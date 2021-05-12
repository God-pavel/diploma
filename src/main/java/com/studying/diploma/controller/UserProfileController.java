package com.studying.diploma.controller;

import com.studying.diploma.dto.UserDTO;
import com.studying.diploma.model.Role;
import com.studying.diploma.model.User;
import com.studying.diploma.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserProfileController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("{user}")
    public String profilePage(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "profile";
    }

    @GetMapping("{user}/edit")
    public String adminEditPage(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "profile_edit";
    }

    @PostMapping("{user}/edit")
    public String profileEdit(@PathVariable User user,
                              Model model,
                              UserDTO userdto) {
        if (!userService.userEdit(user, userdto)) {
            model.addAttribute("message", messageSource.getMessage("message.exist.user", null, LocaleContextHolder.getLocale()));
            return "profile_edit";
        }
        return "redirect:/profile/" + user.getId();
    }
}

