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
import java.util.Scanner;

/**
 *
 * @author Tanner Kendall <tannerkendall@gmail.com>
 */
public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {

    public static String PRODUCT_FILE = "Data/product.txt";
    public static final String DELIMITER = ",";
    ArrayList<Product> productList = new ArrayList<>();

    public FlooringMasteryProductDaoFileImpl() {
        PRODUCT_FILE = "Data/product.txt";
    }
    
    public FlooringMasteryProductDaoFileImpl(String productTextFile){
        PRODUCT_FILE = productTextFile;
    }

    @Override
    public Product unmarshallProduct(String productAsText) throws FlooringMasteryPersistanceException {
        try{
            // productAsText is expecitng a line read in from our file.
            // Then we split that line on the DELIMITER, in this case ",",
            // leaving an array of strings stored in productTokens.
            // pattern: ProductType,CostPerSquareFoot,LaborCostPerSquareFoot

            String[] productTokens = productAsText.split(DELIMITER);

            //productType as [0]
            String productType = productTokens[0];

            Product productFromFile = new Product(productType);

            productFromFile.setCostPerSquareFoot(new BigDecimal(productTokens[1]).setScale(2, RoundingMode.HALF_UP));

            productFromFile.setLaborCostPerSquareFoot(new BigDecimal(productTokens[2]).setScale(2, RoundingMode.HALF_UP));

            return productFromFile;
        }catch(NumberFormatException e){
            throw new FlooringMasteryPersistanceException("INVALID PRODUCT LIST");
        }
        
    }

    @Override
    public ArrayList<Product> loadProducts() throws FlooringMasteryPersistanceException {
        
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistanceException("-_- Could not load product data into memory.", e);
        }
        
        // currentLine will hold most recent line read from file
        String currentLine;
        
        // currentProduct holds most recent Product unmarshalled
        Product currentProduct = null;
        
        // go through PRODUCT_FILE line by line, decoding each into a Product object by calling
        //unmarshallObject method.
        // while we still have more lines in the file
        while(scanner.hasNextLine()){
            //skip first line
            //scanner.skip("ProductType");
            //scanner.skip("CostPerSquareFoot");
            //scanner.skip("LaborCostPerSquareFoot");
            //get next line
            currentLine = scanner.nextLine();
            
            try{
                //unmarshall into a product
                currentProduct = unmarshallProduct(currentLine);

                //use productType as key in map
                productList.add(currentProduct);
            }catch(FlooringMasteryPersistanceException e){
                e.getMessage();
            }
        }
        //close scanner
        scanner.close();
        return productList;
    }

}
