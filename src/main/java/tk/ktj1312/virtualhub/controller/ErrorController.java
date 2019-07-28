package tk.ktj1312.virtualhub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected Map<String,String> EntityNotFoundException(EntityNotFoundException e){
        Map<String ,String> result = new HashMap<>();
        result.put("message",e.getMessage());
        return result;
    }

    @ExceptionHandler(value = {JsonProcessingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Map<String,String> JsonProcessingException(JsonProcessingException e){
        Map<String ,String> result = new HashMap<>();
        result.put("message",e.getMessage());
        return result;
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    protected ResponseEntity<?> HttpClientException(HttpClientErrorException e){

        Map<String ,String> result = new HashMap<>();
        result.put("message",e.getMessage());

        return new ResponseEntity<>(result,e.getStatusCode());
    }
}
