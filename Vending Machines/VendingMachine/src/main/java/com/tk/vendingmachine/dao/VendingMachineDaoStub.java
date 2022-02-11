/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dao;

import com.tk.vendingmachine.dto.Snack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineDaoStub implements VendingMachineDao {

    private Map<Integer, Snack> snacks = new HashMap<>();
    
    @Override
    public List<Snack> listSnacks() throws VendingMachineDaoException, VendingMachinePersistanceException {
        return new ArrayList<Snack>(snacks.values());
    }

    @Override
    public void purchaseSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException {
        
        Snack purchasedSnack = snacks.get(selectionNum);
        purchasedSnack.setInventoryOfItem(purchasedSnack.getInventoryOfItem() - 1);
        
    }

    @Override
    public Snack getSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException {
        return snacks.get(selectionNum);
    }
    
    @Override
    public void addSnack(int selectionNum, Snack snack){
        snacks.put(selectionNum, snack);
    }
    
}
