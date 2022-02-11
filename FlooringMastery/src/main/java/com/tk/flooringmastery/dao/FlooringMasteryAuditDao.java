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
public interface FlooringMasteryAuditDao {
    
    public void writeAuditEntry(String entry) throws FlooringMasteryPersistanceException;
    
}
