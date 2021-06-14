package com.studying.diploma.validator;

import org.springframework.stereotype.Component;

@Component
public class RecipeValidator {

    private final NamesValidator namesValidator;

    public RecipeValidator(NamesValidator namesValidator) {
        this.namesValidator = namesValidator;
    }

    public Result validate(Integer time,
                           String name) {
        if (!namesValidator.validate(name).isValid())
            return new Result(false, "Recipe name is not valid");
        if (time < 1 || time > 300)
            return new Result(false, "Recipe time is not valid");

        return new Result(true);

    }
}
