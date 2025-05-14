package com.lynas.redcare.validator;

import com.lynas.redcare.exception.InvalidInputException;

public class InputValidator {
    public static void validateLanguage(String language) {
        if (!language.matches("[a-zA-Z]+")) {
            throw new InvalidInputException("Language must contain only letters : [ " + language + " ]");
        }
    }
}
