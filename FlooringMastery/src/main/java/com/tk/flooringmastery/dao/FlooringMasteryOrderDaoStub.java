/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import static com.tk.flooringmastery.dao.FlooringMasteryOrderDaoFileImpl.dtf;
import com.tk.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryOrderDaoStub implements FlooringMasteryOrderDao{

    List<Order> orders = new ArrayList<>();
    private Map<LocalDate, List<Order>> ordersMap = new HashMap<>();
    Order testOrder;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    public FlooringMasteryOrderDaoStub(){
        
        testOrder = new Order();
        testOrder.setOrderNumber(1);
        testOrder.setCustomerName("Ada Lovelace");
        testOrder.setState("KY");
        testOrder.setTaxRate(new BigDecimal("6.00"));
        testOrder.setProductType("Carpet");
        testOrder.setArea(new BigDecimal("15.00").setScale(2, RoundingMode.HALF_UP));
        testOrder.setCostPerSquareFoot(new BigDecimal("2.25").setScale(2, RoundingMode.HALF_UP));
        testOrder.setLaborCostPerSquareFoot(new BigDecimal(2.10));
        testOrder.setMaterialCost(testOrder.calculateMaterialCost(testOrder.getArea(), testOrder.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        testOrder.setLaborCost(testOrder.calculateLaborCost(testOrder.getArea(), testOrder.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP));
        testOrder.setTax(testOrder.calculateTax(testOrder.getMaterialCost(), testOrder.getLaborCost(), testOrder.getTaxRate()).setScale(2, RoundingMode.HALF_UP));
        testOrder.setTotal(testOrder.calculateTotal(testOrder.getMaterialCost(), testOrder.getLaborCost(), testOrder.getTax()).setScale(2, RoundingMode.HALF_UP));
        testOrder.setOrderDate(LocalDate.parse("01/01/2022", dtf));
        
    }
    
    @Override
    public List<Order> getAllOrdersByDate(LocalDate ordersDate) throws FlooringMasteryPersistanceException {
        if(ordersMap.containsKey(ordersDate)){
            orders = ordersMap.get(ordersDate);
            return orders;
        } else{
            return null;
        }
        
    }

    @Override
    public Order addOrder(int orderNumber, Order order) throws FlooringMasteryPersistanceException {
        if (orderNumber == order.getOrderNumber()){
            if(ordersMap.containsKey(order.getOrderDate())){
                orders.add(order);
                ordersMap.put(order.getOrderDate(), orders);
                return order;
            }else{
                orders.clear();
                orders.add(order);
                ordersMap.put(order.getOrderDate(), orders);
                return order;
            }
        }else{
            return null;
        }
        
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNumber) throws FlooringMasteryPersistanceException {
        if (ordersMap.containsKey(orderDate)){
            orders = ordersMap.get(orderDate);
            Order order = orders.remove(orderNumber - 1);
            ordersMap.put(orderDate, orders);
            return order;
        }else{
            return null;
        }
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNumber, Order updatedOrder) throws FlooringMasteryPersistanceException {
        
        Order oldOrder = removeOrder(orderDate, orderNumber);
        Order newOrder = oldOrder;
        newOrder.setOrderNumber(oldOrder.getOrderNumber());
        addOrder(newOrder.getOrderNumber(), newOrder);
        return newOrder;
    }

    @Override
    public Order getOrder(LocalDate ordersDate, int orderNumber) throws FlooringMasteryPersistanceException {
        if(ordersMap.containsKey(ordersDate)){
            orders = ordersMap.get(ordersDate);
            return orders.get(orderNumber - 1);
        }
        return null;
    }

}
