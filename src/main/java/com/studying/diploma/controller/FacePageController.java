package com.studying.diploma.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class FacePageController {
    private final MessageSource messageSource;

    public FacePageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String greeting(Model model) {
        model.addAttribute("message", messageSource.getMessage("message.greeting", null, LocaleContextHolder.getLocale()));
        return "face";
    }
}
