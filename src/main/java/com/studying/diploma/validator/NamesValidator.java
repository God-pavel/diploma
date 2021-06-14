package com.studying.diploma.validator;

import org.springframework.stereotype.Component;

@Component

public class NamesValidator implements Validator<String> {
    private String regex = "^[a-zA-Zа-яА-Я]{3,16}$";
    private String message = "Invalid name";

    @Override
    public Result validate(String name) {
        if (name.matches(regex)) {
            return new Result(true);
        } else {
            return new Result(false, message);
        }
    }
}
