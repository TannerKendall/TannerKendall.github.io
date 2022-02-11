/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryProductDaoFileImplTest {
    
    FlooringMasteryProductDao testDao;
    
    public FlooringMasteryProductDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        
        testDao = ctx.getBean("productStub", FlooringMasteryProductDaoStub.class);
        
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testLoadProducts() throws Exception{
        ArrayList<Product>productList = testDao.loadProducts();
        
        assertEquals(productList.size(), 2);
        
    }
    
}
