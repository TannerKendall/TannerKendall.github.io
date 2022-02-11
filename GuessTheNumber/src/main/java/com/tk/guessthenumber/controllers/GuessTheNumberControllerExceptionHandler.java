/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.controllers;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author Tanner Kendall
 */
@ControllerAdvice
@RestController
public class GuessTheNumberControllerExceptionHandler  extends ResponseEntityExceptionHandler{
    
    private static final String CONSTRAINT_MESSAGE = "Could not make guess. Please ensure it is valid and try again.";
    
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
        SQLIntegrityConstraintViolationException ex,
        WebRequest request){
        
        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
        
    }
    
    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Error> handleNullPointerException(
        NullPointerException ex,
        WebRequest request){
        
        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
        
    }
    
    
}


/**
 * @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Error> MysqlDataTruncation(
        MysqlDataTruncation ex,
        WebRequest request){
        
        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
        
    }
 */