/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
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
public class FlooringMasteryTaxDaoFileImplTest {
    
    FlooringMasteryTaxDao testDao;
    
    public FlooringMasteryTaxDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationcontext.xml");
        
        testDao = ctx.getBean("taxStub", FlooringMasteryTaxDaoStub.class);
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLoadTaxes() throws Exception{
        HashMap<String, BigDecimal> taxMap = testDao.loadTaxes();
        
        
        assertEquals(taxMap.size(), 2);
        assertEquals(taxMap.get("TX"), new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP));
        assertEquals(taxMap.get("WA"), new BigDecimal("9.25"));
    }
    
}
