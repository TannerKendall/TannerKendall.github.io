/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Product;
import java.util.ArrayList;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public interface FlooringMasteryProductDao {

    
    /**
     * Unmarshalls product object from text file
     * @param productAsText
     * @return Product object
     * @throws FlooringMasteryPersistanceException 
     */
    public Product unmarshallProduct(String productAsText) throws FlooringMasteryPersistanceException;
    
    /**
     * Returns map filled with product info
     * Key: ProductType
     * Value: Product
     * @return ArrayList
     * @throws FlooringMasteryPersistanceException 
     */
    public ArrayList<Product>loadProducts() throws FlooringMasteryPersistanceException;
    
    
}
