/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dao;

import com.tk.vendingmachine.dto.Snack;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tanner Kendall
 */
public interface VendingMachineDao {
    
    
    /**
     * Returns list of all snacks with selectionNum, Name, Price, and Quantity
     * @return List of available snacks
     * @throws VendingMachineDaoException
     * @throws VendingMachinePersistanceException
     */
    List<Snack> listSnacks() throws VendingMachineDaoException, VendingMachinePersistanceException;
    
    /**
     * Returns purchased Snack and subtracts quantity by 1
     *
     * @param selectionNum number associated with selecting item for purchase
     * @throws VendingMachineDaoException
     * @throws VendingMachinePersistanceException
     */
    void purchaseSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException;
    
    /**
     * Returns Snack associated with given selectionNum
     * @return Snack associated with given selectionNum
     * @param selectionNum
     * @throws VendingMachineDaoException
     * @throws VendingMachinePersistanceException
     */
    Snack getSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException;
    
    
    /**
     * adds snack to map for testing purpose
     * @param selectionNum
     * @param snack
     */
    void addSnack(int selectionNum, Snack snack);
}
