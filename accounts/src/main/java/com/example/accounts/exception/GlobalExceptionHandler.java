package com.example.accounts.exception;

import com.example.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice // this tells springboot whenever exception happens in any of the controller invoke a method from this class
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//GLobal exception will extend ResponseEntityExceptionHandler class, so if validation will fail, it will come to this GlobalExceptionHandler class.
    @ExceptionHandler(Exception.class) //it handles all kind of exception checked and unchecked.
    public ResponseEntity<ErrorResponseDto> handlGlobalException(Exception exception,
                                                                            WebRequest webRequest){
        // web request is used to get the api path that my customer has used
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),// when its false we will get only api information, if we want more info like ip address and all we can set it true
                HttpStatus.INTERNAL_SERVER_ERROR,// Run timer error ocuured
                exception.getMessage(),// this message comes from Exception class
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);// first parameter errorReponseDto is the body and second parameter is staus
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class) // which method to invoke when exception happened is decided by @ExceptionHandler or it contains the method name which need to be invoked when exception happened
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException exception,
                                                                                 WebRequest webRequest){
        // web request is used to get the api path that my customer has used
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),// when its false we will get only api information, if we want more info like ip address and all we can set it true
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),// this message comes from ResourceNotFoundException class
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);// first parameter errorReponseDto is the body and second parameter is staus
    }
    @ExceptionHandler(ResourceNotFoundException.class) // which method to invoke when exception happened is decided by @ExceptionHandler or it contains the method name which need to be invoked when exception happened
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                                 WebRequest webRequest){
        // web request is used to get the api path that my customer has used
        ErrorResponseDto errorResponseDTO = new ErrorResponseDto(
                webRequest.getDescription(false),// when its false we will get only api information, if we want more info like ip address and all we can set it true
                HttpStatus.NOT_FOUND,
                exception.getMessage(),// this message comes from CustomerAlreadyExistsException class
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);// first parameter errorReponseDto is the body and second parameter is staus
    }

    // below method is imported from ResponseEntityHandler class, for validation
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //MethodArgumentNotValidException this contains all the validation exception which has occurred .
        HashMap<String,String> validationErrors= new HashMap<>();
       List<ObjectError> validationErroList=ex.getBindingResult().getAllErrors(); // getting list of validation exception

       validationErroList.forEach((error)->{
           String fieldName=((FieldError)error).getField(); // getting fieldname of exception
           String validationMsg=error.getDefaultMessage();// getting message for validation exception
           validationErrors.put(fieldName,validationMsg);
       });
       return new ResponseEntity<>(validationErrors,HttpStatus.BAD_REQUEST);

    }


}
