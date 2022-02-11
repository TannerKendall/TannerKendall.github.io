/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.vendingmachine;

import com.tk.vendingmachine.controller.VendingMachineController;
import com.tk.vendingmachine.dao.VendingMachinePersistanceException;
import com.tk.vendingmachine.service.InsufficientFundsException;
import com.tk.vendingmachine.service.NoItemInInventoryException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tanner Kendall
 */
public class App {
    public static void main(String[] args) {
        
//        UserIO myIo = new UserIOConsoleImpl();
        
//        VendingMachineView myView = new VendingMachineView(myIo);
        
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        
//        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        
//        Change myChange = new Change();
        
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao, myChange);
        
//        VendingMachineController controller = new VendingMachineController(myService, myView);
        
//        controller.run();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        
        controller.run();

    }
}
