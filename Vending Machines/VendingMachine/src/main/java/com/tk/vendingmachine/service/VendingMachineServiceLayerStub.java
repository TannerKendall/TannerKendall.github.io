/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.service;

import com.tk.vendingmachine.dao.VendingMachineAuditDao;
import com.tk.vendingmachine.dao.VendingMachineDao;
import com.tk.vendingmachine.dao.VendingMachineDaoException;
import com.tk.vendingmachine.dao.VendingMachineDaoStub;
import com.tk.vendingmachine.dao.VendingMachinePersistanceException;
import com.tk.vendingmachine.dto.Change;
import com.tk.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineServiceLayerStub implements VendingMachineServiceLayer {

    VendingMachineDao dao = new VendingMachineDaoStub();
    Change change = new Change();
    private Map<Integer, Snack> snacks = new HashMap<>();
    
    public VendingMachineServiceLayerStub(VendingMachineDao dao, Change change){
        this.dao = dao;
        this.change = change;
    }
    
    @Override
    public List<Snack> listSnacks() throws VendingMachineDaoException, VendingMachinePersistanceException {
        return dao.listSnacks().stream()
                              .filter((snack)-> snack.getInventoryOfItem() > 0)
                              .collect(Collectors.toList());
    }

    @Override
    public String purchaseSnack(int selectionNum, BigDecimal deposit) throws VendingMachineDaoException, VendingMachinePersistanceException, 
                                                                                    InsufficientFundsException, NoItemInInventoryException {
        if(deposit.equals(BigDecimal.ZERO) || deposit.compareTo(BigDecimal.ZERO) == -1){
            throw new InsufficientFundsException("Please add more funds.");
        }
        Snack snack = dao.getSnack(selectionNum);
        //BigDecimal depositBD = new BigDecimal(depositString);
        
        if(snack.getInventoryOfItem() <= 0){
            throw new NoItemInInventoryException("Sorry, " + snack.getName() + " is sold out.");
        }
        
        if(deposit.compareTo(snack.getCost()) == -1) {
            throw new InsufficientFundsException("Please add more funds.");
        }
        
        change.makeChange(snack, deposit);
        String changeString = "You are receiving " + change.getDollars() + " dollars, " + 
                "\n " + change.getQuarters() + " quarters, " + 
                "\n " + change.getDimes() + " dimes, " +
                "\n " + change.getNickels() + " nickels, " +
                "\n " + change.getPennies() + " pennies";
        
        dao.purchaseSnack(selectionNum);
        
        return changeString;
    }

    @Override
    public Snack getSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException {
       return dao.getSnack(selectionNum);
    }

    @Override
    public void addSnack(int selectionNum, Snack snack) {
        dao.addSnack(selectionNum, snack);
    }
    
    
    
}
