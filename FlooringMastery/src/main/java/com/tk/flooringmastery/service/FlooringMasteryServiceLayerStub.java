/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.service;

import com.tk.flooringmastery.dao.FlooringMasteryAuditDao;
import com.tk.flooringmastery.dao.FlooringMasteryOrderDao;
import com.tk.flooringmastery.dao.FlooringMasteryPersistanceException;
import com.tk.flooringmastery.dao.FlooringMasteryProductDao;
import com.tk.flooringmastery.dao.FlooringMasteryTaxDao;
import com.tk.flooringmastery.dto.Order;
import com.tk.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryServiceLayerStub implements FlooringMasteryServiceLayer{
    
    FlooringMasteryOrderDao orderDao;
    FlooringMasteryTaxDao taxDao;
    FlooringMasteryProductDao productDao;
    
    HashMap<String, BigDecimal> taxRates;
    ArrayList<Product> products;
    List<Order> ordersForDate = new ArrayList<>();
    
    public FlooringMasteryServiceLayerStub(FlooringMasteryOrderDao orderDao, FlooringMasteryTaxDao taxDao,
            FlooringMasteryProductDao productDao) throws FlooringMasteryPersistanceException {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        
        taxRates = taxDao.loadTaxes();
        products = productDao.loadProducts();
        
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate ordersDate) throws FlooringMasteryPersistanceException {
        return orderDao.getAllOrdersByDate(ordersDate);
    }

    @Override
    public Order addOrder(int orderNum, Order order) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException {
        try{
            validateOrder(order);
        }catch(FlooringMasteryOrderValidationException e){
            throw new FlooringMasteryOrderValidationException("CANNOT VALIDATE ORDER INFO");
        }
        
        return orderDao.addOrder(orderNum, order);
        
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException {
        ordersForDate = orderDao.getAllOrdersByDate(orderDate);
        
        if(ordersForDate.isEmpty()){
            throw new FlooringMasteryPersistanceException("NO ORDERS FOR DATE");
        } else{
            //auditDao.writeAuditEntry("ORDER REMOVED");
            Order removedOrder = orderDao.removeOrder(orderDate, orderNumber);
            if(removedOrder == null){
                throw new FlooringMasteryPersistanceException("NO ORDER");
            }else{
                return removedOrder;
            }
        }
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order updatedOrder) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException {
        try{
            updatedOrder = validateOrder(orderDao.editOrder(orderDate, orderNumber, updatedOrder));
            return updatedOrder;
        }catch(FlooringMasteryOrderValidationException e){
            return null;
        }
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException, FlooringMasteryOrderValidationException {
        try{
            ordersForDate = orderDao.getAllOrdersByDate(orderDate);
        }catch(FlooringMasteryPersistanceException e){
            throw new FlooringMasteryPersistanceException("CANNOT LOAD ORDERS");
        }
        
        if(ordersForDate.isEmpty()){
            throw new FlooringMasteryOrderValidationException("NO ORDERS EXIST FOR DATE");
        }else{
            return orderDao.getOrder(orderDate, orderNumber);
        }
    }

    @Override
    public Order validateOrder(Order order) throws FlooringMasteryOrderValidationException {
        if(order.getOrderNumber() == 0 || order.getCustomerName() == null || order.getCustomerName().trim().length() == 0
                || order.getState() == null || order.getState().trim().length() == 0 || 
                order.getProductType() == null || order.getProductType().trim().length() == 0 || order.getOrderDate() == null){
            throw new FlooringMasteryOrderValidationException("ERROR: ALL FIELDS REQUIRED TO SAVE ORDER");
        }else{
            return order;
        }
    }

    @Override
    public HashMap<String, BigDecimal> getTaxRates() {
        return taxRates;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public int getNextOrderNumber(LocalDate orderDate){
        try{
        ordersForDate = getAllOrdersByDate(orderDate);
        }catch(FlooringMasteryPersistanceException e){
            //do nothing
        }
        if(ordersForDate.isEmpty()){
            return 1;
        } else{
            List<Integer> orderNumbers = ordersForDate.stream().map(Order::getOrderNumber).collect(Collectors.toList());
            int maxOrderNumber = (Collections.max(orderNumbers) + 1);
            return maxOrderNumber;
        }
    }
}
