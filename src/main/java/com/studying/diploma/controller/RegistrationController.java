package com.studying.diploma.controller;

import com.studying.diploma.dto.UserDTO;
import com.studying.diploma.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;
    private final MessageSource messageSource;

    public RegistrationController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String registrationPage() {
        return "registration";
    }


    @PostMapping
    public String addUser(UserDTO userdto, Model model) {
//        Result checkDTO = new UserDtoValidator(new PasswordValidator(),new UsernameValidator()).validate(userdto);
//
//        if (!checkDTO.isValid()) {
//            model.put("message", checkDTO.getMessage());
//            log.warn(checkDTO.getMessage());
//            return "registration";
//        }
        if (!userService.saveNewUser(userdto)) {
            model.addAttribute("message", messageSource.getMessage("message.exist.user", null, LocaleContextHolder.getLocale()));
            return "registration";
        }

        return "redirect:/login";
    }
}
