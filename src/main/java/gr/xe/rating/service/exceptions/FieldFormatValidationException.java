package gr.xe.rating.service.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown in case of field format error
 */
public class FieldFormatValidationException extends RuntimeException {

    private static final long serialVersionUID = -4313297951095453558L;

    public FieldFormatValidationException(String fieldName) {
        super("Invalid".concat(fieldName).concat("FormatValue"));
    }
}