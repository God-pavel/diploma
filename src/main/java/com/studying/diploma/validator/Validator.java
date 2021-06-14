package com.studying.diploma.validator;

public interface Validator<T> {

    Result validate(T value);
}
