/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.controller;


import com.tk.vendingmachine.dao.VendingMachineDaoException;
import com.tk.vendingmachine.dao.VendingMachinePersistanceException;
import com.tk.vendingmachine.dto.Snack;
import com.tk.vendingmachine.service.InsufficientFundsException;
import com.tk.vendingmachine.service.NoItemInInventoryException;
import com.tk.vendingmachine.service.VendingMachineServiceLayer;
import com.tk.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineController {
    
    private VendingMachineView view;;
    private VendingMachineServiceLayer service;
    
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }
    
    public void run() {
        
        boolean keepGoing = true;
        int menuSelection;
        
        displayMachine();
        
        try {
            while(keepGoing){
                menuSelection = printMenuAndGetSelection();
                
                switch(menuSelection){
                    case 1:
                        try{
                            try{
                                try{
                                    purchaseSnack();
                                break;
                                } catch (VendingMachineDaoException e){
                                    view.displayErrorMessage(e.getMessage());
                                }
                            }catch(InsufficientFundsException e){
                                view.displayErrorMessage(e.getMessage());
                            }
                        }catch (NoItemInInventoryException e){
                            view.displayErrorMessage(e.getMessage());
                        }
                        break;
                    case 2:
                        try{
                            listSnacks();
                        break;
                        } catch (VendingMachineDaoException e){
                            view.displayErrorMessage(e.getMessage());
                        }
                        break;
                    case 3:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
                
            }
        } catch (VendingMachinePersistanceException e) {
            view.displayErrorMessage(e.getMessage());
        }
        
    }
    
    public void displayMachine(){
        view.displayMachine();
    }
    
    private int printMenuAndGetSelection(){
        return view.printMenuAndGetSelection();
    }
    
    private void unknownCommand(){
        view.displayUnknownCommandMessage();
    }
    
    private void listSnacks() throws VendingMachineDaoException, VendingMachinePersistanceException{
        view.displayDisplayAllBanner();
        List<Snack> snackList = service.listSnacks();
        view.displaySnackList(snackList);
    }
    
    private void purchaseSnack() throws VendingMachineDaoException, VendingMachinePersistanceException,
                                    InsufficientFundsException, NoItemInInventoryException {
        
        try{
            int selectionNumber = view.getPurchaseSelection();
            BigDecimal deposit = view.displayDepositFundsRequest();
            String changeString = service.purchaseSnack(selectionNumber, deposit);
            view.giveChange(changeString);
        }catch(InsufficientFundsException e){
            view.displayInsufficientFunds();
        }
        
    }
    
}
