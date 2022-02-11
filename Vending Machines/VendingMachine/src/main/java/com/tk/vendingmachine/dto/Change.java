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
public class Change {
    
    Snack snack;
    Currency currency;
    private int pennies, nickels, dimes, quarters, dollars;
    
    public void makeChange(Snack snack, BigDecimal deposit){
        
        pennies = 0;
        nickels = 0;
        dimes = 0;
        quarters = 0;
        dollars = 0;
        
        BigDecimal cost = snack.getCost();
        
        BigDecimal change = deposit.subtract(cost).setScale(2, RoundingMode.HALF_UP);
        
        while(change.floatValue() > 0){
            
            if(change.compareTo(currency.DOLLAR.getValueOf()) == 1 ||
                    change.compareTo(currency.DOLLAR.getValueOf()) == 0){
                
                change = change.subtract(currency.DOLLAR.getValueOf());
                dollars++;
                
            } else if(change.compareTo(currency.QUARTER.getValueOf()) == 1 ||
                    change.compareTo(currency.QUARTER.getValueOf()) == 0){
                
                change = change.subtract(currency.QUARTER.getValueOf());
                quarters++;
                
            } else if(change.compareTo(currency.DIME.getValueOf()) == 1 ||
                    change.compareTo(currency.DIME.getValueOf()) == 0){
                
                change = change.subtract(currency.DIME.getValueOf());
                dimes++;
                
            } else if(change.compareTo(currency.NICKEL.getValueOf()) == 1 ||
                    change.compareTo(currency.NICKEL.getValueOf()) == 0){
                
                change = change.subtract(currency.NICKEL.getValueOf());
                nickels++;
                
            } else if(change.compareTo(currency.PENNY.getValueOf()) == 1 ||
                    change.compareTo(currency.PENNY.getValueOf()) == 0){
                
                change = change.subtract(currency.PENNY.getValueOf());
                pennies++;
            }
        }
        
    }

    public int getPennies() {
        return pennies;
    }

    public int getNickels() {
        return nickels;
    }

    public int getDimes() {
        return dimes;
    }

    public int getQuarters() {
        return quarters;
    }

    public int getDollars() {
        return dollars;
    }
    
}