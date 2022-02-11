/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.service;

import com.tk.flooringmastery.dao.FlooringMasteryPersistanceException;
import com.tk.flooringmastery.dto.Order;
import com.tk.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public interface FlooringMasteryServiceLayer {
    
    /**
     * Returns a list of all orders placed on a certain date, if there are no errors
     * display an error message and return to main menu
     * @param ordersDate
     * @return List of orders
     * @throws FlooringMasteryPersistanceException 
     */
    List<Order> getAllOrdersByDate(LocalDate ordersDate) throws FlooringMasteryPersistanceException;
    
    /**
     * Returns a new order and writes it to a file
     * @param orderNum
     * @param order
     * @return created order
     * @throws FlooringMasteryPersistanceException 
     * @throws com.tk.flooringmastery.service.FlooringMasteryOrderValidationException 
     */
    Order addOrder(int orderNum, Order order) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException;
    
    /**
     * Takes in date and orderNumber and returns an order to be removed from a file,
     * if no order exists, display error message and return to main menu
     * @param orderDate
     * @param orderNumber
     * @return removed order
     * @throws FlooringMasteryPersistanceException 
     * @throws com.tk.flooringmastery.service.FlooringMasteryOrderValidationException 
     */
    Order removeOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException;
    
    /**
     * Takes in date and order number, allows user to edit info and overwrites previous info.If no order exists display error message and return to main menu
     * @param orderDate
     * @param orderNumber
     * @param updatedOrder
     * @return editedOrder
     * @throws FlooringMasteryPersistanceException 
     * @throws com.tk.flooringmastery.service.FlooringMasteryOrderValidationException 
     */
    Order editOrder(LocalDate orderDate, int orderNumber, Order updatedOrder) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException;
    
    /**
     * Takes in order number and returns associated order
     * @param orderNumber
     * @param orderDate
     * @return Order
     * @throws FlooringMasteryPersistanceException
     * @throws com.tk.flooringmastery.service.FlooringMasteryOrderValidationException
     */
    Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException;
    
    /**
     * Validates order for adding
     * @param order
     * @return
     * @throws FlooringMasteryOrderValidationException 
     */
    Order validateOrder(Order order) throws FlooringMasteryOrderValidationException;
    
    HashMap<String, BigDecimal> getTaxRates();
    
    ArrayList<Product> getProducts();
    
    /**
     * Takes in orderDate and finds nextOrderNumber
     * @param orderDate
     * @return int
     */
    int getNextOrderNumber(LocalDate orderDate);
    
}

