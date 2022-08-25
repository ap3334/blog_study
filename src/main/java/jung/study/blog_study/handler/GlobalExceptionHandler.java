package jung.study.blog_study.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<String> handleArgumentException(Exception e) {
        return new ResponseEntity("<h1>" + e.getMessage() + "</h1>", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
