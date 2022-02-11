/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine.dao;

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
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        
        //testDao  = new VendingMachineDaoStub();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        testDao = ctx.getBean("vendingMachineDaoStub", VendingMachineDaoStub.class);
        
        Snack snack1 = new Snack("Dorito's");
        snack1.setCost("1.75");
        snack1.setInventoryOfItem(5);
        snack1.setItemId(1);
        
        Snack snack2 = new Snack("Frito's");
        snack2.setCost("2.00");
        snack2.setInventoryOfItem(5);
        snack2.setItemId(2);
        
        testDao.addSnack(1, snack1);
        testDao.addSnack(2, snack2);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetSnack() throws Exception{
        
        Snack testSnack1 = testDao.getSnack(1);
        
        assertEquals(testSnack1.getName(), "Dorito's", "checking name..");
        assertEquals(testSnack1.getCost(), new BigDecimal("1.75"), "checking cost..");
        assertEquals(testSnack1.getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(testSnack1.getItemId(), 1, "checking selectionNum..");
        
        assertEquals(testDao.getSnack(1).getName(), "Dorito's", "checking name..");
        assertEquals(testDao.getSnack(1).getCost(), new BigDecimal("1.75"), "checking cost..");
        assertEquals(testDao.getSnack(1).getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(testDao.getSnack(1).getItemId(), 1, "checking selectionNum..");
        
        Snack testSnack2 = testDao.getSnack(2);
        
        assertEquals(testSnack2.getName(), "Frito's", "checking name..");
        assertEquals(testSnack2.getCost(), new BigDecimal("2.00"), "checking cost..");
        assertEquals(testSnack2.getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(testSnack2.getItemId(), 2, "checking selectionNum..");
        
        assertEquals(testDao.getSnack(2).getName(), "Frito's", "checking name..");
        assertEquals(testDao.getSnack(2).getCost(), new BigDecimal("2.00"), "checking cost..");
        assertEquals(testDao.getSnack(2).getInventoryOfItem(), 5, "checking numLeft");
        assertEquals(testDao.getSnack(2).getItemId(), 2, "checking selectionNum..");
        
    }
    
    @Test
    public void testGetAllSnacks() throws Exception{
        
        List<Snack> allSnacks = testDao.listSnacks();
        
        assertEquals(allSnacks.size(), 2, "checking list size..");
        
        assertEquals(allSnacks.get(0).getName(), "Dorito's", "checking");
        assertEquals(allSnacks.get(1).getName(), "Frito's", "checking");
        
    }
    
    @Test void testPurchaseSnack() throws Exception{
        
        Snack purchasedSnack = testDao.getSnack(1);
        
        assertEquals(purchasedSnack.getInventoryOfItem(), 5, "checking numLeft");
        testDao.purchaseSnack(1);
        assertEquals(purchasedSnack.getInventoryOfItem(), 4, "checking numLeft");
        
    }
    
}
