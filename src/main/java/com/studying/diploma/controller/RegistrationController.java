package com.studying.diploma.controller;

import com.studying.diploma.dto.UserDTO;
import com.studying.diploma.service.UserService;
import com.studying.diploma.validator.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final UserDtoValidator userDtoValidator;

    public RegistrationController(UserService userService, MessageSource messageSource, UserDtoValidator userDtoValidator) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.userDtoValidator = userDtoValidator;
    }

    @GetMapping
    public String registrationPage() {
        return "registration";
    }


    @PostMapping
    public String addUser(UserDTO userdto, Model model) {
        Result result = userDtoValidator.validate(userdto);

        if (!result.isValid()) {
            model.addAttribute("message", result.getMessage());
            log.warn(result.getMessage());
            return "registration";
        }
        if (!userService.saveNewUser(userdto)) {
            model.addAttribute("message", messageSource.getMessage("message.exist.user", null, LocaleContextHolder.getLocale()));
            return "registration";
        }

        return "redirect:/login";
    }
}
