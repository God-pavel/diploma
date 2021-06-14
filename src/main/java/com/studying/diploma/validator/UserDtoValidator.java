package com.studying.diploma.validator;


import com.studying.diploma.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDtoValidator implements Validator<UserDTO> {

    private final PasswordValidator passwordValidator;
    private final UsernameValidator usernameValidator;
    private final NamesValidator namesValidator;

    public UserDtoValidator(PasswordValidator passwordValidator, UsernameValidator usernameValidator, NamesValidator namesValidator) {
        this.passwordValidator = passwordValidator;
        this.usernameValidator = usernameValidator;
        this.namesValidator = namesValidator;
    }


    @Override
    public Result validate(UserDTO dto) {
        if (!usernameValidator.validate(dto.getUsername()).isValid())
            return new Result(false, usernameValidator.validate(dto.getUsername()).getMessage());
        if (!passwordValidator.validate(dto.getPassword()).isValid())
            return new Result(false, passwordValidator.validate(dto.getPassword()).getMessage());
        if (!namesValidator.validate(dto.getName()).isValid())
            return new Result(false, "Name is not valid");
        if (!namesValidator.validate(dto.getName()).isValid())
            return new Result(false, "Surname is not valid");

        return new Result(true);

    }
}