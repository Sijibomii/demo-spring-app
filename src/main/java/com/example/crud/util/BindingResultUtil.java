package com.example.crud.util;

import org.springframework.validation.BindingResult;

public class BindingResultUtil {

    @SuppressWarnings({"null"})
    public static MessageResult validate(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            // https://web.archive.org/web/20161120115428/http://codetutr.com/2013/05/28/spring-mvc-form-validation/
            String message = bindingResult.getFieldError().getDefaultMessage();
            System.out.println(bindingResult.getFieldError());
            return MessageResult.error(500, message);
        }else {
            return null;
        }
    }
}
