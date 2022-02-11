/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.flooringmastery.dao;

import com.tk.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryTaxDaoFileImpl implements FlooringMasteryTaxDao {

    public static String TAX_FILE = "Data/tax.txt";
    public static final String DELIMITER = ",";
    HashMap<String, BigDecimal> taxMap = new HashMap<>();
    
    public FlooringMasteryTaxDaoFileImpl() {
        TAX_FILE = "Data/tax.txt";
    }
    
    public FlooringMasteryTaxDaoFileImpl(String taxTextFile){
        TAX_FILE = taxTextFile;
    }

    @Override
    public Tax unmarshallTax(String taxAsText) throws FlooringMasteryPersistanceException {
        
        try{
            // taxAsText is expecitng a line read in from our file.
            // Then we split that line on the DELIMITER, in this case ",",
            // leaving an array of strings stored in taxTokens.
            // pattern: State,StateName,TaxRate

            String[] taxTokens = taxAsText.split(DELIMITER);

            //stateAbbreviation as [0]
            String stateAbbreviation = taxTokens[0];

            Tax taxFromFile = new Tax(stateAbbreviation);

            taxFromFile.setStateName(taxTokens[1]);

            taxFromFile.setTaxRate(new BigDecimal(taxTokens[2]).setScale(2, RoundingMode.HALF_UP));

            return taxFromFile;
        }catch(NumberFormatException e){
            throw new FlooringMasteryPersistanceException("INVALID TAX FILE");
        }
    }
    
    @Override
    public HashMap<String, BigDecimal> loadTaxes() throws FlooringMasteryPersistanceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistanceException("-_- Could not load tax data into memory.", e);
        }
        
        // currentLine will hold most recent line read from file
        String currentLine;
        
        // currentTax holds most recent Product unmarshalled
        Tax currentTax;
        
        // go through TAX_FILE line by line, decoding each into a Tax object by calling
        //unmarshallObject method.
        // while we still have more lines in the file
        while(scanner.hasNextLine()){
            //skip first line
            //scanner.skip("State,StateName,TaxRate");
            //get next line
            currentLine = scanner.nextLine();
            
            try{
                //unmarshall into a Tax Object
                currentTax = unmarshallTax(currentLine);

                //use stateAbbreviation as key in map
                taxMap.put(currentTax.getStateAbbreviation(), currentTax.getTaxRate());
            }catch(FlooringMasteryPersistanceException e){
                e.getMessage();
            }
        }
        //close scanner
        scanner.close();
        return taxMap;
    }
    
    
}