/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.service;

import com.tk.vendingmachine.dao.VendingMachineAuditDao;
import com.tk.vendingmachine.dao.VendingMachineDao;
import com.tk.vendingmachine.dao.VendingMachineDaoException;
import com.tk.vendingmachine.dao.VendingMachinePersistanceException;
import com.tk.vendingmachine.dto.Change;
import com.tk.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    VendingMachineAuditDao auditDao;
    VendingMachineDao dao;
    Change change;
    String soldOut;
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao, Change change){
        this.dao = dao;
        this.auditDao = auditDao;
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
        
        try{

            if(deposit.equals(BigDecimal.ZERO) || deposit.compareTo(BigDecimal.ZERO) == -1){
                throw new InsufficientFundsException("Please add more funds.");
            }

            Snack snack = dao.getSnack(selectionNum);

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
            auditDao.writeAuditEntry("Snack: " + snack.getName() + " purchased.");

            return changeString;

        }catch(NoItemInInventoryException e){
            soldOut = "Sorry, this item is sold out";
        }
        return soldOut;
    }

    @Override
    public Snack getSnack(int selectionNum) throws VendingMachineDaoException, VendingMachinePersistanceException {
        return dao.getSnack(selectionNum);
    }

    @Override
    public void addSnack(int selectionNum, Snack snack) {
        
    }
}
