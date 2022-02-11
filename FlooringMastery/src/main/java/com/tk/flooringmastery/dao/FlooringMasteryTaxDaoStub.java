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

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryTaxDaoStub implements FlooringMasteryTaxDao{

    @Override
    public HashMap<String, BigDecimal> loadTaxes() throws FlooringMasteryPersistanceException {
        HashMap<String, BigDecimal> taxMap = new HashMap<>();
        taxMap.put("TX", new BigDecimal("4.45").setScale(2, RoundingMode.HALF_UP));
        taxMap.put("WA", new BigDecimal("9.25"));
        
        return taxMap;
    }

    @Override
    public Tax unmarshallTax(String taxAsText) throws FlooringMasteryPersistanceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
