package com.tc.website.webapi.utils;


import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ValidationUtil {

    public static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = ((ObjectError)bindingResult.getAllErrors().get(0)).getDefaultMessage();
            throw new ValidationException(message);
        }
    }
}