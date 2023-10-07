package com.geetodolist.todolistapps.exception;

import com.geetodolist.todolistapps.dto.ErrorObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle exception if any request is exsist
    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<ErrorObject> handleItemExistsException(ItemAlreadyExistException ex, WebRequest request){
        ErrorObject theErrorObject = new ErrorObject();
        theErrorObject.setStatusCode(HttpStatus.CONFLICT.value());
        theErrorObject.setMessage(ex.getMessage());
        theErrorObject.setTimeStamp(new Date());

        return new ResponseEntity<ErrorObject>(theErrorObject, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());


        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("messages", errors);
        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }

}
