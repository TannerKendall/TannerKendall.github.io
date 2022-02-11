/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.service;

import com.tk.vendingmachine.dao.VendingMachineDaoException;
import com.tk.vendingmachine.dao.VendingMachinePersistanceException;
import com.tk.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public interface VendingMachineServiceLayer {
    
    List<Snack> listSnacks() throws VendingMachineDaoException, VendingMachinePersistanceException;
    
    String purchaseSnack(int selectionNumb, BigDecimal deposit) throws VendingMachineDaoException, VendingMachinePersistanceException,
                                    InsufficientFundsException, NoItemInInventoryException;
    
    Snack getSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException;
    
    void addSnack(int selectionNum, Snack snack);
    
}
