/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryProductDaoStub implements FlooringMasteryProductDao {

    ArrayList<Product> productList = new ArrayList<>();
    
    public FlooringMasteryProductDaoStub() {
        
        Product carpet = new Product("Carpet");
        carpet.setCostPerSquareFoot(new BigDecimal("2.25"));
        carpet.setLaborCostPerSquareFoot(new BigDecimal("2.10").setScale(2, RoundingMode.HALF_UP));
        
        Product laminate = new Product("Laminate");
        carpet.setCostPerSquareFoot(new BigDecimal("1.75").setScale(2, RoundingMode.HALF_UP));
        carpet.setLaborCostPerSquareFoot(new BigDecimal("2.10").setScale(2, RoundingMode.HALF_UP));
        
        productList.add(carpet);
        productList.add(laminate);
        
    }

    
    @Override
    public Product unmarshallProduct(String productAsText) throws FlooringMasteryPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Product> loadProducts() throws FlooringMasteryPersistanceException {
        
        return productList;
    }

}
