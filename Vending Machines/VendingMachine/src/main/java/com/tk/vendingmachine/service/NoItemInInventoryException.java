/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.service;

/**
 *
 * @author Tanner Kendall
 */
public class NoItemInInventoryException extends Exception {
    
    public NoItemInInventoryException(String message){
        super(message);
    }
    
    public NoItemInInventoryException(String message, Throwable cause){
        super(message, cause);
    }
    
}
