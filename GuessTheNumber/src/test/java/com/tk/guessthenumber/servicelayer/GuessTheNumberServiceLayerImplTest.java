/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.guessthenumber.servicelayer;

import com.tk.guessthenumber.TestApplicationConfiguration;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tanner Kendall
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessTheNumberServiceLayerImplTest {
    
    @Autowired
    GuessTheNumberServiceLayer service;
    
    public GuessTheNumberServiceLayerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetResult1() {
        
        String guess = "1234";
        String answer = "1234";
        
        String result = service.getResult(guess, answer);
        
        assertEquals("e:4:p:0", result);
        
    }
    
    @Test
    public void testGetResult2() {
        
        String guess = "4321";
        String answer = "1234";
        
        String result = service.getResult(guess, answer);
        
        assertEquals("e:0:p:4", result);
        
    }
    
    @Test
    public void testGetResult3() {
        
        String guess = "4321";
        String answer = "8675";
        
        String result = service.getResult(guess, answer);
        
        assertEquals("e:0:p:0", result);
        
    }
    
    @Test
    public void testGetResult4() {
        
        String guess = "8576";
        String answer = "8675";
        
        String result = service.getResult(guess, answer);
        
        assertEquals("e:2:p:2", result);
        
    }
    
}
