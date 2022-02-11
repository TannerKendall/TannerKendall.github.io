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
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryOrderDaoFileImplTest {
    
    FlooringMasteryOrderDao testDao;
    
    public FlooringMasteryOrderDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        
        testDao = ctx.getBean("orderStub", FlooringMasteryOrderDaoStub.class);
        
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
        
        testDao.addOrder(order.getOrderNumber(), order);
        Order orderCompare = testDao.getOrder(order.getOrderDate(), order.getOrderNumber());
        orderCompare.setOrderNumber(2);
        
        assertEquals(order.getOrderNumber(), orderCompare.getOrderNumber());
        assertEquals(order.getCustomerName(), orderCompare.getCustomerName());
        
        List<Order> orders = testDao.getAllOrdersByDate(order.getOrderDate());
        
        assertEquals(orders.size(), 1);
        
        testDao.addOrder(orderCompare.getOrderNumber(), orderCompare);
        
        assertEquals(orders.size(), 2);
        
        Order differentDateOrder = order;
        differentDateOrder.setOrderNumber(3);
        differentDateOrder.setOrderDate(LocalDate.parse("01/01/2023", dtf));
        
        testDao.addOrder(differentDateOrder.getOrderNumber(), differentDateOrder);
        
        orders = testDao.getAllOrdersByDate(differentDateOrder.getOrderDate());
        
        assertEquals(orders.size(), 1);
        
    }
    
    @Test
    public void testRemoveOrder()throws Exception{
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
        
        testDao.addOrder(order.getOrderNumber(), order);
        
        List<Order> orders = testDao.getAllOrdersByDate(order.getOrderDate());
        
        assertEquals(orders.size(), 1);
        
        Order removedOrder = testDao.removeOrder(order.getOrderDate(), order.getOrderNumber());
        
        orders = testDao.getAllOrdersByDate(order.getOrderDate());
        
        assertEquals(orders.size(), 0);
        
    }
    
    @Test
    public void testGetEditOrder()throws Exception{
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
        
        testDao.addOrder(order.getOrderNumber(), order);
        
        order = testDao.editOrder(order.getOrderDate(), order.getOrderNumber(), order);
        
        Order order2 = testDao.getOrder(order.getOrderDate(), order.getOrderNumber());
        
        assertEquals(order.getOrderNumber(), 1);
        
        assertEquals(order2.getOrderNumber(), order.getOrderNumber());
        
    }
}
