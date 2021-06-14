package com.studying.diploma.validator;

import org.springframework.stereotype.Component;

@Component
public class IngredientValidator {

    public Result validate(Long weight) {
        if (weight < 1 || weight > 5000)
            return new Result(false, "Weight is not valid");

        return new Result(true);
    }
}
