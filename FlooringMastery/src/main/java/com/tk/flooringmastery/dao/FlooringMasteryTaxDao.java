/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public interface FlooringMasteryTaxDao {
    
//    /**
//     * Returns a list of all taxes and associated info
//     * @return list of tax info
//     * @throws FlooringMasteryPersistanceException
//     */
//    List<Tax> getAllTaxInfo() throws FlooringMasteryPersistanceException;
    
//    /**
//     * Returns a Tax object
//     * @return Tax Object
//     * @param stateAbbreviation
//     * @throw FlooringMasteryPersistanceException
//     */
//    Tax getTax(String stateAbbreviation) throws FlooringMasteryPersistanceException;
    
    /**
     * Returns map filled with tax info
     * Key: StateAbbreviation
     * Value: TaxRate
     * @return hashMap
     * @throws FlooringMasteryPersistanceException 
     */
    public HashMap<String, BigDecimal>loadTaxes() throws FlooringMasteryPersistanceException;
    
    /**
     * Unmarshalls tax object from text file
     * @param taxAsText
     * @return Tax object
     * @throws FlooringMasteryPersistanceException 
     */
    public Tax unmarshallTax(String taxAsText) throws FlooringMasteryPersistanceException;
    
}
