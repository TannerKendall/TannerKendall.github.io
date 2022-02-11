/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.controller;

import com.tk.flooringmastery.dao.FlooringMasteryPersistanceException;
import com.tk.flooringmastery.dto.Order;
import com.tk.flooringmastery.service.FlooringMasteryOrderValidationException;
import com.tk.flooringmastery.service.FlooringMasteryServiceLayer;
import com.tk.flooringmastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryController {
    
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service){
        this.view = view;
        this.service = service;
    }
    
    public void run(){
        
        boolean keepGoing = true;
        int menuSelection;
        view.displayTsgBanner();
        while(keepGoing){
            
            menuSelection = printMenuAndGetSelection();
            
            switch(menuSelection){
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportData();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                    
            }
            
        }
        
    }
    
    private int printMenuAndGetSelection(){
        return view.printMenuAndGetSelection();
    }
    
    private void displayOrders(){
        try{
            view.displayOrderList(service.getAllOrdersByDate(view.getDate()));
        }catch(FlooringMasteryPersistanceException e){
            view.displayNoOrdersList();
        }
    }
    
    private void addOrder(){
        
        view.displayBanner("ADD ORDER");
        try{
            try{
                try{
                    Order newOrder = view.addOrder(service.getProducts(), service.getTaxRates());
                    newOrder.setOrderNumber(service.getNextOrderNumber(newOrder.getOrderDate()));
                    view.displayOrder(newOrder);
                    if(newOrder != null){
                        service.addOrder(newOrder.getOrderNumber(), view.getConfirmationToAdd(newOrder));
                        view.displayBanner("SUCCESS");
                    }
                }catch(NullPointerException e){
                    view.displayIfNo("ORDER NOT ADDED.");
                }
            }catch(FlooringMasteryOrderValidationException e){
                view.displayBanner("ERROR: CANNOT VALIDATE ORDER.");
            }
        }catch(FlooringMasteryPersistanceException e){
            view.displayBanner("ERROR");
        }
        
    }
    
    private void editOrder(){
        view.displayBanner("EDIT ORDER");
        LocalDate date;
        int orderNumber;
        
        try{
            date = view.getDate();
            List<Order> orderList = (service.getAllOrdersByDate(date));
            
            view.displayOrderList(orderList);
            orderNumber = view.getOrderToEdit(orderList, service.getNextOrderNumber(date));
            
            try{
                Order updatedOrder = view.editOrder(service.getOrder(date, orderNumber), service.getProducts(), service.getTaxRates());
                if(updatedOrder == null){
                    view.displayIfNo("ORDER NOT EDITED");
                }else {
                    service.editOrder(date, updatedOrder.getOrderNumber(), updatedOrder);
                    view.displayBanner("SUCCESS");
                }
            }catch(FlooringMasteryOrderValidationException e){
                view.displayBanner("ERROR: CANNOT VALIDATE ORDER.");
            }
        }catch(FlooringMasteryPersistanceException e){
            view.displayBanner("ERROR: NO SUCH ORDER");
        }
        
    }
    
    private void removeOrder(){
        
        view.displayBanner("REMOVE ORDER");
        
        try{
            try{
                Order removedOrder = service.getOrder(view.getDate(), view.getOrderNumber());
                
                if(removedOrder == null){
                    view.displayBanner("ERROR: NO SUCH ORDER");
                }else{
                    view.displayOrder(removedOrder);
                    removedOrder = view.getConfirmationToRemove(removedOrder);
                    try{
                        service.removeOrder(removedOrder.getOrderDate(), removedOrder.getOrderNumber());
                        view.displayBanner("SUCCESS");
                    }catch(NullPointerException e){
                        view.displayIfNo("ORDER NOT REMOVED");
                    }
                }
                
            }catch(FlooringMasteryPersistanceException e){
                view.displayBanner("ERROR: NO SUCH ORDER");
            }
        }catch(FlooringMasteryOrderValidationException e){
            view.displayBanner("ERROR: NO SUCH ORDER");
        }
    }
    
    private void exportData(){
        view.featureNotAdded();
    }
    
    private void unknownCommand(){
        view.displayBanner("UNKNOWN COMMAND");
    }
    
}
