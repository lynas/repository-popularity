package com.lynas.redcare.validator;

import com.lynas.redcare.exception.InvalidInputException;
import org.junit.jupiter.api.Test;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InputValidatorTest {

    @Test
    void validateLanguage_shouldNotThrow_whenValidLanguage() {
        InputValidator.validateLanguage("English");
        InputValidator.validateLanguage("Deutsch");
        // No exception means test passes
    }

    @Test
    void validateLanguage_shouldThrow_whenInvalidLanguage() {
        assertThatThrownBy(() -> InputValidator.validateLanguage("En-glish123"))
                .isInstanceOf(InvalidInputException.class)
                .hasMessageContaining("Language must contain only letters");
    }

    @Test
    void getErrorMessageForInvalidInput_shouldReturnCustomMessageForLastUpdatedAt() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "not-a-date", String.class, "lastUpdatedAt", null, new RuntimeException("Invalid type"));

        String message = InputValidator.getErrorMessageForInvalidInput(ex);
        assertThat(message).isEqualTo("Invalid lastUpdatedAt field : [ not-a-date ]");
    }

    @Test
    void getErrorMessageForInvalidInput_shouldReturnDefaultMessageForOtherParams() {
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("someOtherParam");
        when(ex.getMessage()).thenReturn("Default error message");

        String message = InputValidator.getErrorMessageForInvalidInput(ex);
        assertThat(message).isEqualTo("Default error message");
    }
}