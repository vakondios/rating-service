package gr.xe.rating.service.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Slf4j
public class GenericMappingException extends RuntimeException {

    private static final long serialVersionUID = 5443931329280066125L;

    public GenericMappingException(String fromClassName, String toClassName) {
        super("System cannot mapping the model " + fromClassName + " to model " + toClassName);
    }
}
