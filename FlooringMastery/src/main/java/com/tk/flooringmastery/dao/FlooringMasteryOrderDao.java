/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public interface FlooringMasteryOrderDao {
    
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
     */
    Order addOrder(int orderNum, Order order) throws FlooringMasteryPersistanceException;
    
    /**
     * Takes in date and orderNumber and returns an order to be removed from a file,
     * if no order exists, display error message and return to main menu
     * @param orderDate
     * @param orderNumber
     * @return removed order
     * @throws FlooringMasteryPersistanceException 
     */
    Order removeOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException;
    
    /**
     * Takes in date and order number, allows user to edit info and overwrites previous info.
     * If no order exists display error message and return to main menu
     * @param orderDate
     * @param orderNumber
     * @return editedOrder
     * @throws FlooringMasteryPersistanceException 
     */
    Order editOrder(LocalDate orderDate, int orderNumber, Order editedOrder) throws FlooringMasteryPersistanceException;
    
    /**
     * Takes in order number and returns associated order
     * @param orderNum
     * @param orderDate
     * @return Order
     * @throws FlooringMasteryPersistanceException
     */
    Order getOrder(LocalDate orderDate, int orderNum) throws FlooringMasteryPersistanceException;
    
}
