/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao {

    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachineDaoException {
        PrintWriter out;
        
        try{
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e){
            throw new VendingMachineDaoException("Could not persist audit information.", e);
        }
        
        LocalDateTime timeStamp = LocalDateTime.now();
        
        out.println(timeStamp.toString() + " : " + entry);
        
        out.flush();
    }
    
}
