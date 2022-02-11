/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.service;

import com.tk.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryServiceLayerImplTest {
    
    
    FlooringMasteryServiceLayer service;
    
    public FlooringMasteryServiceLayerImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        service = ctx.getBean("serviceStub", FlooringMasteryServiceLayerStub.class);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddGetOrdersByDate() throws Exception{
        
        Order order = new Order();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        order.setOrderNumber(1);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00").setScale(2, RoundingMode.HALF_UP));
        order.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
        order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
        order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
        order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));
        order.setOrderDate(LocalDate.parse("01/01/2022", dtf));
        
        service.addOrder(order.getOrderNumber(), order);
        Order orderCompare = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        orderCompare.setOrderNumber(2);
        
        assertEquals(order.getOrderNumber(), orderCompare.getOrderNumber());
        assertEquals(order.getCustomerName(), orderCompare.getCustomerName());
        
        List<Order> orders = service.getAllOrdersByDate(order.getOrderDate());
        
        assertEquals(orders.size(), 1);
        
        service.addOrder(orderCompare.getOrderNumber(), orderCompare);
        
        assertEquals(orders.size(), 2);
        
        Order differentDateOrder = order;
        differentDateOrder.setOrderNumber(3);
        differentDateOrder.setOrderDate(LocalDate.parse("01/01/2023", dtf));
        
        service.addOrder(differentDateOrder.getOrderNumber(), differentDateOrder);
        
        orders = service.getAllOrdersByDate(differentDateOrder.getOrderDate());
        
        assertEquals(orders.size(), 1);
        
    }
    
    @Test
    public void testRemoveOrder() throws Exception{
        Order order = new Order();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        order.setOrderNumber(1);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00").setScale(2, RoundingMode.HALF_UP));
        order.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
        order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
        order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
        order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));
        order.setOrderDate(LocalDate.parse("01/01/2022", dtf));
        
        service.addOrder(order.getOrderNumber(), order);
        
        List<Order> orders = service.getAllOrdersByDate(order.getOrderDate());
        
        assertEquals(orders.size(), 1);
        
        Order removedOrder = service.removeOrder(order.getOrderDate(), order.getOrderNumber());
        
        orders = service.getAllOrdersByDate(order.getOrderDate());
        
        assertEquals(orders.size(), 0);
        
    }
    
    @Test
    public void testEditOrder() throws Exception{
        Order order = new Order();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        order.setOrderNumber(1);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00").setScale(2, RoundingMode.HALF_UP));
        order.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
        order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
        order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
        order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));
        order.setOrderDate(LocalDate.parse("01/01/2022", dtf));
        
        service.addOrder(order.getOrderNumber(), order);
        
        order = service.editOrder(order.getOrderDate(), order.getOrderNumber(), order);
        
        Order order2 = service.getOrder(order.getOrderDate(), order.getOrderNumber());
        
        assertEquals(order.getOrderNumber(), 2);
        
        assertEquals(order2.getOrderNumber(), order.getOrderNumber());
    }
    
    @Test
    public void testValidateOrder()throws Exception{
        Order order = new Order();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        order.setOrderNumber(0);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00").setScale(2, RoundingMode.HALF_UP));
        order.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
        order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
        order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
        order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));
        order.setOrderDate(LocalDate.parse("01/01/2022", dtf));
        
        Assertions.assertThrows(FlooringMasteryOrderValidationException.class, () -> {
            service.validateOrder(order);
        });
        
    }
    
    @Test
    public void testGetNextOrderNumber() throws Exception{
        Order order = new Order();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        
        order.setOrderNumber(1);
        order.setCustomerName("Ada Lovelace");
        order.setState("CA");
        order.setTaxRate(new BigDecimal("25.00").setScale(2, RoundingMode.HALF_UP));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("249.00").setScale(2, RoundingMode.HALF_UP));
        order.setCostPerSquareFoot(new BigDecimal("3.50").setScale(2, RoundingMode.HALF_UP));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15").setScale(2, RoundingMode.HALF_UP));
        order.setMaterialCost(order.calculateMaterialCost(order.getArea(), order.getCostPerSquareFoot()));
        order.setLaborCost(order.calculateLaborCost(order.getArea(), order.getLaborCostPerSquareFoot()));
        order.setTax(order.calculateTax(order.getMaterialCost(), order.getLaborCost(), order.getTaxRate()));
        order.setTotal(order.calculateTotal(order.getMaterialCost(), order.getLaborCost(), order.getTax()));
        order.setOrderDate(LocalDate.parse("01/01/2022", dtf));
        
        service.addOrder(order.getOrderNumber(), order);
        
        int nextOrderNumber = service.getNextOrderNumber(order.getOrderDate());
        
        assertEquals(nextOrderNumber, 2);
        
    }
    
}