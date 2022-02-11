/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public interface UserIO {
    
    void print(String msg);
    
    int readInt(String prompt);
    
    int readInt(String prompt, int min, int max);
    
    String readString(String prompt);
    
    BigDecimal readBigDecimal(String prompt);
    
    LocalDate readLocalDate(String prompt);
    
}
