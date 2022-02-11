/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Tanner Kendall
 */
public enum Currency {
    PENNY(new BigDecimal(".01")),
    NICKEL(new BigDecimal(".05")),
    DIME(new BigDecimal(".10")),
    QUARTER(new BigDecimal(".25")),
    DOLLAR(new BigDecimal("1.00"));
    
    
    private BigDecimal value;
    
    private Currency(BigDecimal value){
        this.value = value;
    }
    
    public BigDecimal getValueOf(){
        return this.value;
    }
}
