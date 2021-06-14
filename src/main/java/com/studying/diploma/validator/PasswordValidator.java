package com.studying.diploma.validator;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements Validator<String> {
    private String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
    private String message = "Invalid password";

    @Override
    public Result validate(String password) {
        if (password.matches(regex)) {
            return new Result(true);
        } else {
            return new Result(false, message);
        }
    }

}
