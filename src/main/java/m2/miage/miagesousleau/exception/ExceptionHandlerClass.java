package m2.miage.miagesousleau.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import m2.miage.miagesousleau.entities.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> errorException(NotFoundException exception, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> errorException(ForbiddenException exception, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ArgumentErrorException.class)
    public ResponseEntity<ExceptionResponse> errorException(ArgumentErrorException exception, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(GeneralErreurException.class)
    public ResponseEntity<ExceptionResponse> errorException(GeneralErreurException exception, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ExceptionResponse> errorException(HttpStatusCodeException exception, WebRequest webRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readValue(exception.getResponseBodyAsString(), JsonNode.class);
        String message = jsonNode.get("messageErreur").asText();
        return new ResponseEntity<>(new ExceptionResponse(exception.getRawStatusCode(), message), exception.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> errorException(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
