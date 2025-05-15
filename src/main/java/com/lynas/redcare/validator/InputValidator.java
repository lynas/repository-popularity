package com.lynas.redcare.validator;

import com.lynas.redcare.exception.InvalidInputException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public class InputValidator {
    private InputValidator() {
    }

    public static void validateLanguage(String language) {
        if (!language.matches("[a-zA-Z]+")) {
            throw new InvalidInputException("Language must contain only letters : [ " + language + " ]");
        }
    }

    public static String getErrorMessageForInvalidInput(MethodArgumentTypeMismatchException ex) {
        var paramName = ex.getName();
        var value = ex.getValue();
        return switch (paramName) {
            case "lastUpdatedAt" -> String.format("Invalid %s field : [ %s ]", paramName, value);
            default -> ex.getMessage();
        };
    }
}
