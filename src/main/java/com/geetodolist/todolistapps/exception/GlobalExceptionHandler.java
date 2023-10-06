package com.geetodolist.todolistapps.exception;

import com.geetodolist.todolistapps.dto.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

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

}
