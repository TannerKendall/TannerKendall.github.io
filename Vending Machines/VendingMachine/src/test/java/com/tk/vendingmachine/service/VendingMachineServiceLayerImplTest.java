/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.service;

import com.tk.vendingmachine.dto.Change;
import com.tk.vendingmachine.dto.Snack;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tanner Kendall
 */
public class VendingMachineServiceLayerImplTest {
    
    VendingMachineServiceLayer service;
    //VendingMachineDao testDao;
    Change change;
    
    public VendingMachineServiceLayerImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        
        //testDao = new VendingMachineDaoStub();
        //change = new Change();
        //service  = new VendingMachineServiceLayerStub(testDao, change);
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        service = ctx.getBean("serviceStub", VendingMachineServiceLayerStub.class);
        change = ctx.getBean("change", Change.class);
        
        Snack snack1 = new Snack("Dorito's");
        snack1.setCost("1.75");
        snack1.setInventoryOfItem(5);
        snack1.setItemId(1);
        
        Snack snack2 = new Snack("Frito's");
        snack2.setCost("2.00");
        snack2.setInventoryOfItem(5);
        snack2.setItemId(2);
        
        service.addSnack(1, snack1);
        service.addSnack(2, snack2);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetSnack() throws Exception {
        Snack testSnack1 = service.getSnack(1);
        
        assertEquals(testSnack1.getName(), "Dorito's", "checking name..");
        assertEquals(testSnack1.getCost(), new BigDecimal("1.75"), "checking cost..");
        assertEquals(testSnack1.getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(testSnack1.getItemId(), 1, "checking selectionNum..");
        
        assertEquals(service.getSnack(1).getName(), "Dorito's", "checking name..");
        assertEquals(service.getSnack(1).getCost(), new BigDecimal("1.75"), "checking cost..");
        assertEquals(service.getSnack(1).getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(service.getSnack(1).getItemId(), 1, "checking selectionNum..");
        
        Snack testSnack2 = service.getSnack(2);
        
        assertEquals(testSnack2.getName(), "Frito's", "checking name..");
        assertEquals(testSnack2.getCost(), new BigDecimal("2.00"), "checking cost..");
        assertEquals(testSnack2.getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(testSnack2.getItemId(), 2, "checking selectionNum..");
        
        assertEquals(service.getSnack(2).getName(), "Frito's", "checking name..");
        assertEquals(service.getSnack(2).getCost(), new BigDecimal("2.00"), "checking cost..");
        assertEquals(service.getSnack(2).getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(service.getSnack(2).getItemId(), 2, "checking selectionNum..");
        
    }
    
    @Test
    public void testGetAllSnacks() throws Exception{
        
        List<Snack> allSnacks = service.listSnacks();
        
        assertEquals(service.listSnacks().size(), 2, "checking list size..");
        assertEquals(allSnacks.size(), 2, "checking list size..");
        
        assertEquals(allSnacks.get(0).getName(), "Dorito's", "checking");
        assertEquals(allSnacks.get(1).getName(), "Frito's", "checking");
        
    }
    
    @Test
    public void testPurchaseSnack() throws Exception{
        assertEquals(service.purchaseSnack(1, new BigDecimal("2.00")), "You are receiving " + change.getDollars() + " dollars, " + 
                                                                                    "\n " + change.getQuarters() + " quarters, " + 
                                                                                    "\n " + change.getDimes() + " dimes, " +
                                                                                     "\n " + change.getNickels() + " nickels, " +
                                                                                     "\n " + change.getPennies() + " pennies", "Checking strings returned with string from method");
        
        assertEquals(service.purchaseSnack(1, new BigDecimal("5.00")), "You are receiving " + change.getDollars() + " dollars, " + 
                                                                                    "\n " + change.getQuarters() + " quarters, " + 
                                                                                    "\n " + change.getDimes() + " dimes, " +
                                                                                     "\n " + change.getNickels() + " nickels, " +
                                                                                     "\n " + change.getPennies() + " pennies");
        
        assertEquals(service.purchaseSnack(2, new BigDecimal("7.00")), "You are receiving " + change.getDollars() + " dollars, " + 
                                                                                    "\n " + change.getQuarters() + " quarters, " + 
                                                                                    "\n " + change.getDimes() + " dimes, " +
                                                                                     "\n " + change.getNickels() + " nickels, " +
                                                                                     "\n " + change.getPennies() + " pennies");
        
        assertEquals(service.purchaseSnack(2, new BigDecimal("4.00")), "You are receiving " + change.getDollars() + " dollars, " + 
                                                                                    "\n " + change.getQuarters() + " quarters, " + 
                                                                                    "\n " + change.getDimes() + " dimes, " +
                                                                                     "\n " + change.getNickels() + " nickels, " +
                                                                                     "\n " + change.getPennies() + " pennies");
         
        
    }
    
    @Test
    public void testPurchaseSnackThrowsInsufficientFundsException() throws Exception{
        boolean thrown = false;
        
        try{
            service.purchaseSnack(1, new BigDecimal("1.00"));
        } catch(InsufficientFundsException e){
            thrown = true;
        }
        assertTrue(thrown);
        
        try{
            service.purchaseSnack(2, new BigDecimal("1.50"));
        } catch(InsufficientFundsException e){
            thrown = true;
        }
        assertTrue(thrown);
    }
    
     @Test
    public void testPurchaseSnackThrowsNoItemInInventoryException() throws Exception{
        
        boolean thrown = false;
        
        service.getSnack(1).setInventoryOfItem(0);
        
        try{
            service.purchaseSnack(1, new BigDecimal("3.00"));
        } catch(NoItemInInventoryException e){
            thrown = true;
        }
        assertTrue(thrown);
        
        service.getSnack(2).setInventoryOfItem(0);
        
        try{
            service.purchaseSnack(2, new BigDecimal("3.00"));
        } catch(NoItemInInventoryException e){
            thrown = true;
        }
        assertTrue(thrown);
        
    }
    
}
