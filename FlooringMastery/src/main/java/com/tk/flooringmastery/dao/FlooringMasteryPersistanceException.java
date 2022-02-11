/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryPersistanceException extends Exception {
    
    public FlooringMasteryPersistanceException(String message){
        super(message);
    }
    
    public FlooringMasteryPersistanceException(String message, Throwable cause){
        super(message, cause);
    }
    
}
